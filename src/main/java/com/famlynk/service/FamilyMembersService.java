package com.famlynk.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.famlynk.dto.FamilyTreeResponse;
import com.famlynk.dto.MutualConnectionResponse;
import com.famlynk.model.FamilyMemberMapping;
import com.famlynk.model.FamilyMembers;
import com.famlynk.model.FamilyRelation;
import com.famlynk.model.Register;
import com.famlynk.model.SecondLevelRelation;
import com.famlynk.model.ThirdLevelRelation;
import com.famlynk.repository.IFamilyMemberMappingRepository;
import com.famlynk.repository.IFamilyMembersRepository;
import com.famlynk.repository.IFamilyRelationRepo;
import com.famlynk.repository.IRegisterRepository;
import com.famlynk.repository.ISecondLevelRelationRepository;
import com.famlynk.repository.IThirdLevelRelationRepository;
import com.famlynk.util.Constants;
import com.famlynk.util.EmailSender;
import com.famlynk.util.IdGenerator;

import io.micrometer.common.util.StringUtils;

@Service
public class FamilyMembersService {

	@Autowired
	public IFamilyMembersRepository familyMembersRepository;

	@Autowired
	public IFamilyMemberMappingRepository familyMemberMappingRepository;
	@Autowired
	private IFamilyRelationRepo familyRelationRepo;
	@Autowired
	public NotifyService notifyService;
	@Autowired
	public EmailSender emailSender;
	@Autowired
	public IRegisterRepository registerRepository;
	@Autowired
	public CacheManager cacheManager;
	@Autowired
	public ISecondLevelRelationRepository secondLevelRelationRepository;
	@Autowired
	public IThirdLevelRelationRepository thirdLevelRelationRepository;

	public FamilyMembers addFamilyMember(FamilyMembers familyMember) {
		Date date = (Date) Calendar.getInstance().getTime();
		familyMember.setCreatedOn(date);
		String uUserId = IdGenerator.randomUniqueUserId(familyMember.getName());
		familyMember.setUniqueUserID(uUserId);
		getSideForFamilyMember(familyMember);
		Register register = registerRepository.findByUserId(familyMember.getUserId());
		familyMember.setRelation(
				getPrimaryRelation(Constants.USER, getRelationForFamilyMember(familyMember), register.getGender())
						.getRelationship());
		FamilyMembers familyMemberObj = familyMembersRepository.save(familyMember);
		Thread myThread = new Thread(() -> {
			evictFamilyTreeCache();
			getFamilyMembers(familyMemberObj);
			Register registerObj = registerRepository.findByUserId(familyMember.getUserId());
			if (familyMember.getEmail() != null) {
				emailSender.sendEmailForAddFamily(familyMember.getEmail(), registerObj.getName(),
						familyMember.getName(), getPrimaryRelation(Constants.USER,
								getRelationForFamilyMember(familyMember), familyMember.getGender()).getRelationship());
			}
		});
		myThread.start();
		return familyMemberObj;

	}

	/**
	 * create a {@link FamilyMemberMapping}
	 * 
	 * @param inFamMember
	 * @param familyMember
	 * @param gender
	 * @param inRelation
	 */
	private void createFamilyMemberMapping(FamilyMembers inFamMember, FamilyMembers familyMember, String gender,
			String inRelation) {
		FamilyMemberMapping familyMemberMapping = new FamilyMemberMapping();
		familyMemberMapping.setId(null);
		familyMemberMapping.setPrimaryMemberId(inFamMember.getUniqueUserID());
		familyMemberMapping.setSecondaryMemberId(familyMember.getUniqueUserID());
		familyMemberMapping.setUserId(inFamMember.getUserId());
		familyMemberMapping.setGender(familyMember.getGender());
		familyMemberMapping.setName(familyMember.getName());
		familyMemberMapping.setImage(familyMember.getImage());
		familyMemberMapping.setSide(familyMember.getSide());
		String relation = familyMember.getFirstLevelRelation();
		if (StringUtils.isNotBlank(familyMember.getThirdLevelRelation())) {
			relation = familyMember.getThirdLevelRelation();
			familyMemberMapping.setRelationship(getPrimaryRelation(inRelation, relation, gender).getRelationship());
			familyMemberMapping.setFamilyLevel(getPrimaryRelation(inRelation, relation, gender).getLevel());
		} else if (StringUtils.isNotBlank(familyMember.getSecondLevelRelation())) {
			relation = familyMember.getSecondLevelRelation();
			familyMemberMapping.setRelationship(getPrimaryRelation(inRelation, relation, gender).getRelationship());
			familyMemberMapping.setFamilyLevel(getPrimaryRelation(inRelation, relation, gender).getLevel());
		}
		familyMemberMapping.setRelationship(getPrimaryRelation(inRelation, relation, gender).getRelationship());
		familyMemberMapping.setFamilyLevel(getPrimaryRelation(inRelation, relation, gender).getLevel());
		familyMemberMappingRepository.save(familyMemberMapping);
	}

	/**
	 * create a {@link FamilyMemberMapping} for swapping parameters
	 * 
	 * @param inFamMember
	 * @param familyMember
	 * @param gender
	 * @param inRelation
	 */
	private void createFamilySecondMemberMapping(FamilyMembers inFamMember, FamilyMembers familyMember, String gender,
			String inRelation) {
		FamilyMemberMapping familyMemberMapping = new FamilyMemberMapping();
		familyMemberMapping.setId(null);
		familyMemberMapping.setPrimaryMemberId(inFamMember.getUniqueUserID());
		familyMemberMapping.setSecondaryMemberId(familyMember.getUniqueUserID());
		familyMemberMapping.setUserId(inFamMember.getUserId());
		familyMemberMapping.setGender(familyMember.getGender());
		familyMemberMapping.setName(familyMember.getName());
		familyMemberMapping.setImage(familyMember.getImage());
		familyMemberMapping.setSide(familyMember.getSide());
		String relation = inFamMember.getFirstLevelRelation();
		if (StringUtils.isNotBlank(inFamMember.getThirdLevelRelation())) {
			relation = inFamMember.getThirdLevelRelation();
			familyMemberMapping.setRelationship(getPrimaryRelation(relation, inRelation, gender).getRelationship());
			familyMemberMapping.setFamilyLevel(getPrimaryRelation(relation, inRelation, gender).getLevel());
		} else if (StringUtils.isNotBlank(inFamMember.getSecondLevelRelation())) {
			relation = inFamMember.getSecondLevelRelation();
			familyMemberMapping.setRelationship(getPrimaryRelation(relation, inRelation, gender).getRelationship());
			familyMemberMapping.setFamilyLevel(getPrimaryRelation(relation, inRelation, gender).getLevel());
		}
		familyMemberMapping.setRelationship(getPrimaryRelation(relation, inRelation, gender).getRelationship());
		familyMemberMapping.setFamilyLevel(getPrimaryRelation(relation, inRelation, gender).getLevel());
		familyMemberMappingRepository.save(familyMemberMapping);

	}

	/**
	 * Mapping is done for the added family members using uniqueUserID
	 * 
	 * @param inFamMember
	 */
	private void getFamilyMembers(FamilyMembers inFamMember) {
		List<FamilyMembers> familyMemberList = familyMembersRepository.findByUserId(inFamMember.getUserId());
		for (FamilyMembers familyMember : familyMemberList) {
			String gender = null;
			if (familyMember.getFirstLevelRelation().equals(Constants.USER)
					|| getRelationForFamilyMember(inFamMember).equals("Sister's Husband")) {
				gender = familyMember.getGender();
			}
			if (inFamMember.getRelation() != null) {
				inFamMember.setFirstLevelRelation(inFamMember.getRelation());
			}
			if (!(inFamMember.getUniqueUserID().equals(familyMember.getUniqueUserID()))) {
				createFamilyMemberMapping(inFamMember, familyMember, gender, inFamMember.getFirstLevelRelation());
				createFamilySecondMemberMapping(familyMember, inFamMember, gender, inFamMember.getFirstLevelRelation());
			}
		}
	}

	public FamilyMembers updateFamilyMember(FamilyMembers familyMember, String famid) {
		familyMemberMappingRepository.deleteByPrimaryMemberIdOrSecondaryMemberIdAndUserId(
				familyMember.getUniqueUserID(), familyMember.getUniqueUserID(), familyMember.getUserId());
		Date date1 = (Date) Calendar.getInstance().getTime();
		familyMember.setModifiedOn(date1);
		getSideForFamilyMember(familyMember);
		familyMember.setRelation(
				getPrimaryRelation(Constants.USER, getRelationForFamilyMember(familyMember), familyMember.getGender())
						.getRelationship());

		FamilyMembers familyMemberObj = familyMembersRepository.save(familyMember);
		getFamilyMembers(familyMemberObj);
		List<FamilyMemberMapping> familyMemberMappingList = familyMemberMappingRepository
				.findBySecondaryMemberId(familyMemberObj.getUniqueUserID());
		for (FamilyMemberMapping familyMemberMapping : familyMemberMappingList) {
			familyMemberMapping.setImage(familyMember.getImage());
			familyMemberMappingRepository.save(familyMemberMapping);
		}
		evictFamilyTreeCache();
		return familyMemberObj;
	}

	public List<FamilyMembers> getFamilyMembersByuserId(String userId) {
		return familyMembersRepository.findByUserId(userId);
	}

	public List<FamilyTreeResponse> getFamilyTree(String userId, String uniqueUserID, String level) {
		List<FamilyTreeResponse> familyTreeResponseList = new ArrayList<>();
		Register register = registerRepository.findByUniqueUserID(uniqueUserID);

		if (register != null) {
			List<FamilyMemberMapping> famMemberMappingList = familyMemberMappingRepository
					.findByPrimaryMemberIdAndUserIdAndFamilyLevel(uniqueUserID, register.getUserId(), level);
			for (FamilyMemberMapping familyMemberMapping : famMemberMappingList) {
				FamilyTreeResponse familyTreeResponse = new FamilyTreeResponse();
				familyTreeResponse.setName(familyMemberMapping.getName());
				familyTreeResponse.setImage(getValidImagePath(familyMemberMapping.getImage(),
						familyMemberMapping.getRelationship(), familyMemberMapping.getGender()));
				familyTreeResponse.setSide(familyMemberMapping.getSide());
				familyTreeResponse.setRelationShip(familyMemberMapping.getRelationship());
				familyTreeResponse.setUserId(familyMemberMapping.getUserId());
				familyTreeResponse.setUniqueUserID(familyMemberMapping.getSecondaryMemberId());
				familyTreeResponse.setGender(familyMemberMapping.getGender());

				if (level.equals("firstLevel")) {
					addFirstLevelRelation(familyMemberMapping.getSecondaryMemberId(), familyTreeResponse,
							familyMemberMapping.getRelationship());
				} else if (level.equals("secondLevel")) {
					addSecondLevelRelation(familyMemberMapping.getSecondaryMemberId(), familyTreeResponse,
							familyMemberMapping.getRelationship());
				} else {
					addThirdLevelRelation(familyMemberMapping.getSecondaryMemberId(), familyTreeResponse,
							familyMemberMapping.getRelationship());
				}

				familyTreeResponseList.add(familyTreeResponse);
			}
		} else {
			List<FamilyMemberMapping> famMemberMappingList = familyMemberMappingRepository
					.findByPrimaryMemberIdAndUserIdAndFamilyLevel(uniqueUserID, userId, level);
			for (FamilyMemberMapping familyMemberMapping : famMemberMappingList) {
				FamilyTreeResponse familyTreeResponse = new FamilyTreeResponse();
				familyTreeResponse.setName(familyMemberMapping.getName());
				familyTreeResponse.setImage(getValidImagePath(familyMemberMapping.getImage(),
						familyMemberMapping.getRelationship(), familyMemberMapping.getGender()));
				familyTreeResponse.setSide(familyMemberMapping.getSide());
				familyTreeResponse.setRelationShip(familyMemberMapping.getRelationship());
				familyTreeResponse.setUserId(familyMemberMapping.getUserId());
				familyTreeResponse.setUniqueUserID(familyMemberMapping.getSecondaryMemberId());
				familyTreeResponse.setGender(familyMemberMapping.getGender());

				if (level.equals("firstLevel")) {
					addFirstLevelRelation(familyMemberMapping.getSecondaryMemberId(), familyTreeResponse,
							familyMemberMapping.getRelationship());
				} else if (level.equals("secondLevel")) {
					addSecondLevelRelation(familyMemberMapping.getSecondaryMemberId(), familyTreeResponse,
							familyMemberMapping.getRelationship());
				} else {
					addThirdLevelRelation(familyMemberMapping.getSecondaryMemberId(), familyTreeResponse,
							familyMemberMapping.getRelationship());
				}

				familyTreeResponseList.add(familyTreeResponse);
			}
		}
		familyTreeResponseList.add(addCurrentUserDetails(userId, uniqueUserID, level));
		return familyTreeResponseList;
	}

	private String getValidImagePath(String imagePath, String relationship, String gender) {
		if (imagePath == null || imagePath.isEmpty()) {
			switch (relationship.toLowerCase()) {
			case "father":
			case "uncle":
			case "husband":
			case "son-in-law":
			case "brother-in-law":
			case "paternal father":
			case "maternal father":
				return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Funcle.png?alt=media&token=b75ea2f1-23dd-44a3-ab62-8953b8b4ff95";
			case "mother":
			case "wife":
			case "daughter-in-law":
			case "aunt":
			case "sister-in-law":
			case "paternal mother":
			case "maternal mother":
				return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Faunty.png?alt=media&token=07172b2e-4e35-4855-b40b-345e5c674504";
			case "sister":
			case "daughter":
			case "granddaughter":
			case "paternal daughter":
			case "maternal daughter":
			case "great great granddaughter":
			case "great granddaughter":
				return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Fgirl.png?alt=media&token=be9af0ee-924a-4af6-9d5c-1a9ac7519b2b";
			case "brother":
			case "son":
			case "grandson":
			case "paternal son":
			case "maternal son":
			case "great great grandson":
			case "great grandson":
				return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Fboy.png?alt=media&token=fd6325df-0640-4cd2-9fba-2c2419aa8466";
			case "grandfather":
			case "father-in-law":
			case "great grandfather":
			case "great great grandfather":
				return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Fgrandpa.png?alt=media&token=5a006b6e-c58b-41f2-b227-766ea5454a27";
			case "grandmother":
			case "mother-in-law":
			case "great grandmother":
			case "great great grandmother":
				return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Fgrandma.png?alt=media&token=38950203-0bcc-49e2-8820-68099d26f1a6";
			case "cousin":
			case "in-law":
				return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Funknown.jpg?alt=media&token=dbdd7012-acb9-4b0c-b1ef-6c6c2cdbb186";
			case "user":
				if ("male".equalsIgnoreCase(gender)) {
					return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Funcle.png?alt=media&token=b75ea2f1-23dd-44a3-ab62-8953b8b4ff95";
				} else if ("female".equalsIgnoreCase(gender)) {
					return "https://firebasestorage.googleapis.com/v0/b/famlynk-cdb73.appspot.com/o/userprofileimage%2Faunty.png?alt=media&token=07172b2e-4e35-4855-b40b-345e5c674504";
				}
				break;
			}
		}
		return imagePath;
	}

	/**
	 * Remove the cache
	 */
	public void evictFamilyTreeCache() {
		Cache cache = cacheManager.getCache("familyTreeCache");
		cache.clear();
	}

	/**
	 * adding the current User detail for Familytree
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @param level
	 * @return
	 */
	private FamilyTreeResponse addCurrentUserDetails(String userId, String uniqueUserID, String level) {
		FamilyTreeResponse familyTreeResponse = new FamilyTreeResponse();
		FamilyMembers familyMember = familyMembersRepository.findByUserIdAndUniqueUserID(userId, uniqueUserID);
		if (familyMember != null) {
			familyTreeResponse.setName(familyMember.getName());
			familyTreeResponse
					.setImage(getValidImagePath(familyMember.getImage(), Constants.USER, familyMember.getGender()));
			familyTreeResponse.setRelationShip(Constants.USER);
			familyTreeResponse.setUserId(familyMember.getUserId());
			familyTreeResponse.setGender(familyMember.getGender());
			familyTreeResponse.setUniqueUserID(uniqueUserID);
			familyTreeResponse.setEmail(familyMember.getEmail());
			familyTreeResponse.setNumber(familyMember.getMobileNo());
			familyTreeResponse.setDob(familyMember.getDob());
		}
		addFirstLevelRelation(uniqueUserID, familyTreeResponse, Constants.USER);
		return familyTreeResponse;
	}

	/**
	 * set id for firstLevel relation based on {@link FamilyTreeResponse}
	 * 
	 * @param uniqueUserID
	 * @param familyTreeResponse
	 * @param relationShip
	 */
	public void addFirstLevelRelation(String uniqueUserID, FamilyTreeResponse familyTreeResponse, String relationShip) {
		if (relationShip.equals("son")) {
			familyTreeResponse.setSonId(uniqueUserID);
		} else if (relationShip.equals("daughter")) {
			familyTreeResponse.setdId(uniqueUserID);
		} else if (relationShip.equals("father")) {
			familyTreeResponse.setfId(uniqueUserID);
		} else if (relationShip.equals("mother")) {
			familyTreeResponse.setmId(uniqueUserID);
		} else if (relationShip.equals("brother")) {
			familyTreeResponse.setbId(uniqueUserID);
		} else if (relationShip.equals("sister")) {
			familyTreeResponse.setsId(uniqueUserID);
		} else if (relationShip.equals("wife")) {
			familyTreeResponse.setwId(uniqueUserID);
		} else if (relationShip.equals(Constants.USER) || relationShip.equals("")) {
			familyTreeResponse.setuId(uniqueUserID);
		} else if (relationShip.equals("husband")) {
			familyTreeResponse.sethId(uniqueUserID);
		} else if (relationShip.equals("grandfather")) {
			familyTreeResponse.setgFId(uniqueUserID);
		} else if (relationShip.equals("grandmother")) {
			familyTreeResponse.setgMId(uniqueUserID);
		}
		familyTreeResponse.setUniqueUserID(uniqueUserID);
	}

	/**
	 * set id for secondLevel relation based on {@link FamilyTreeResponse}
	 * 
	 * @param uniqueUserID
	 * @param familyTreeResponse
	 * @param relationShip
	 */
	public void addSecondLevelRelation(String uniqueUserID, FamilyTreeResponse familyTreeResponse,
			String relationShip) {
		if (relationShip.equals("son-in-law")) {
			familyTreeResponse.setSonId(uniqueUserID);
		} else if (relationShip.equals("daughter-in-law")) {
			familyTreeResponse.setSonId(uniqueUserID);
		} else if (relationShip.equals("uncle")) {
			familyTreeResponse.setbId(uniqueUserID);
		} else if (relationShip.equals("aunt")) {
			familyTreeResponse.setbId(uniqueUserID);
		} else if (relationShip.equals("brother-in-law")) {
			familyTreeResponse.setsId(uniqueUserID);
		} else if (relationShip.equals("sister-in-law")) {
			familyTreeResponse.setsId(uniqueUserID);
		} else if (relationShip.equals(Constants.USER) || relationShip.equals("")) {
			familyTreeResponse.setuId(uniqueUserID);
		} else if (relationShip.equals("father-in-law")) {
			familyTreeResponse.setfId(uniqueUserID);
		} else if (relationShip.equals("mother-in-law")) {
			familyTreeResponse.setfId(uniqueUserID);
		} else if (relationShip.equals("grandson")) {
			familyTreeResponse.setdId(uniqueUserID);
		} else if (relationShip.equals("granddaughter")) {
			familyTreeResponse.setdId(uniqueUserID);
		} else if (relationShip.equals("cousin")) {
			familyTreeResponse.setbId(uniqueUserID);
		} else if (relationShip.equals("in-law")) {
			familyTreeResponse.setmId(uniqueUserID);
		} else if (relationShip.equals("paternal father")) {
			familyTreeResponse.setgFId(uniqueUserID);
		} else if (relationShip.equals("paternal mother")) {
			familyTreeResponse.setgFId(uniqueUserID);
		} else if (relationShip.equals("paternal daughter")) {
			familyTreeResponse.setgFId(uniqueUserID);
		} else if (relationShip.equals("paternal son")) {
			familyTreeResponse.setgFId(uniqueUserID);
		} else if (relationShip.equals("maternal mother")) {
			familyTreeResponse.setgMId(uniqueUserID);
		} else if (relationShip.equals("maternal father")) {
			familyTreeResponse.setgMId(uniqueUserID);
		} else if (relationShip.equals("maternal son")) {
			familyTreeResponse.setgMId(uniqueUserID);
		} else if (relationShip.equals("maternal daughter")) {
			familyTreeResponse.setgMId(uniqueUserID);
		}
		familyTreeResponse.setUniqueUserID(uniqueUserID);
	}

	/**
	 * set id for thirdLevel relation based on {@link FamilyTreeResponse}
	 * 
	 * @param uniqueUserID
	 * @param familyTreeResponse
	 * @param relationShip
	 */
	public void addThirdLevelRelation(String uniqueUserID, FamilyTreeResponse familyTreeResponse, String relationShip) {
		if (relationShip.equals("great grandfather")) {
			familyTreeResponse.setfId(uniqueUserID);
		} else if (relationShip.equals("great grandmother")) {
			familyTreeResponse.setmId(uniqueUserID);
		} else if (relationShip.equals("great great granddaughter")) {
			familyTreeResponse.setdId(uniqueUserID);
		} else if (relationShip.equals("great great grandson")) {
			familyTreeResponse.setSonId(uniqueUserID);
		} else if (relationShip.equals(Constants.USER) || relationShip.equals("")) {
			familyTreeResponse.setuId(uniqueUserID);
		} else if (relationShip.equals("great great grandmother")) {
			familyTreeResponse.setgMId(uniqueUserID);
		} else if (relationShip.equals("great great grandfather")) {
			familyTreeResponse.setgFId(uniqueUserID);
		} else if (relationShip.equals("great grandson")) {
			familyTreeResponse.setbId(uniqueUserID);
		} else if (relationShip.equals("great granddaughter")) {
			familyTreeResponse.setsId(uniqueUserID);
		}
		familyTreeResponse.setUniqueUserID(uniqueUserID);
	}

	public FamilyMembers addFamilyMemberByUserIdAndUniqueUserID(String userId, String uniqueUserID,
			FamilyMembers familyMember, boolean notify) {
		FamilyMembers familyMemberObj = familyMembersRepository.findByUniqueUserID(uniqueUserID).get(0);
		familyMemberObj.setFamid(null);
		familyMemberObj.setFirstLevelRelation(familyMember.getFirstLevelRelation());
		familyMemberObj.setSecondLevelRelation(familyMember.getSecondLevelRelation());
		familyMemberObj.setThirdLevelRelation(familyMember.getThirdLevelRelation());
		familyMemberObj.setUserId(userId);
		familyMemberObj.setMobileNo(familyMemberObj.getMobileNo());
		Register register = registerRepository.findByUserId(userId);
		familyMemberObj.setRelation(getPrimaryRelation(getRelationForFamilyMember(familyMember), Constants.USER,
				register.getGender()).getRelationship());
		getSideForFamilyMember(familyMemberObj);
		if (notify) {
			familyMemberObj.setRelation(getPrimaryRelation(Constants.USER, getRelationForFamilyMember(familyMember),
					register.getGender()).getRelationship());
			familyMemberObj.setNotification(
					notifyService.addFamilyNotify(userId, uniqueUserID, familyMemberObj.getRelation()));
		}
		FamilyMembers familyMembers = familyMembersRepository.save(familyMemberObj);
		Thread myThread = new Thread(() -> {
			getFamilyMembers(familyMembers);
			evictFamilyTreeCache();
		});
		myThread.start();
		return familyMembers;
	}

	public FamilyMembers acceptFamilyMember(String userId, String uniqueUserID, String relation,
			String toUniqueUserID) {
		FamilyMembers familyMember = familyMembersRepository.findByUniqueUserIDAndRelation(uniqueUserID, "user").get(0);
		Thread myThread = new Thread(() -> {
			if (familyMember != null) {
				familyMember.setFirstLevelRelation(relation);
				addFamilyMemberByUserIdAndUniqueUserID(userId, uniqueUserID, familyMember, false);
			}
			notifyService.deleteNotify(familyMember.getUserId(), toUniqueUserID);
			evictFamilyTreeCache();
		});
		myThread.start();
		return familyMember;

	}

	public void deleteFamilyMember(String userId, String uniqueUserID) {
		familyMembersRepository.deleteByUniqueUserIDAndUserId(uniqueUserID, userId);
		familyMemberMappingRepository.deleteByPrimaryMemberIdAndUserId(uniqueUserID, userId);
		familyMemberMappingRepository.deleteBySecondaryMemberIdAndUserId(uniqueUserID, userId);
		notifyService.deleteNotify(userId, uniqueUserID);
		evictFamilyTreeCache();
	}

	public String declineFamilyMember(String userId, String uniqueUserID) {
		familyMembersRepository.deleteByUniqueUserIDAndUserId(uniqueUserID, userId);
		familyMemberMappingRepository.deleteByPrimaryMemberIdOrSecondaryMemberIdAndUserId(uniqueUserID, uniqueUserID,
				userId);
		notifyService.deleteNotify(userId, uniqueUserID);
		evictFamilyTreeCache();
		return uniqueUserID;
	}

	/**
	 * This method checks whether the email already exists
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmailExists(String email) {
		return familyMembersRepository.existsByEmail(email);
	}

	/**
	 * This method checks whether the mobile number already exists
	 * 
	 * @param mobileNo
	 * @return
	 */
	public boolean isMobileNoExist(String mobileNo) {
		return familyMembersRepository.existsByMobileNo(mobileNo);
	}

	public List<SecondLevelRelation> getSecondLevelRelation(String firstLevelRelation) {
		return secondLevelRelationRepository.findByFirstLevelRelation(firstLevelRelation);
	}

	public List<ThirdLevelRelation> getThirdLevelRelation(String secondLevelRelation) {
		return thirdLevelRelationRepository.findBySecondLevelRelation(secondLevelRelation);
	}

	public List<FamilyMembers> getMutualConnections(String userId, String uniqueUserID) {
		List<FamilyMembers> familyMembersList = familyMembersRepository.findByUserId(userId);
		List<FamilyMembers> familyMembers = new ArrayList<>();
		Register register = registerRepository.findByUniqueUserID(uniqueUserID);
		if (register != null) {
			List<FamilyMembers> familyMembersObj = familyMembersRepository
					.findByUserIdAndRelationNotIn(register.getUserId(), Constants.USER);
			for (FamilyMembers familyMember : familyMembersList) {
				if (familyMembersObj.contains(familyMember)) {
					familyMembers.add(familyMember);
				}
			}
		}
		return familyMembers;
	}

	public List<MutualConnectionResponse> getMutualConnectionsNames(String userId, String uniqueUserID) {
		List<MutualConnectionResponse> mutualConnectionResponse = new ArrayList<MutualConnectionResponse>();
		List<FamilyMembers> familyMembersList = familyMembersRepository.findByUserId(userId);
		Register register = registerRepository.findByUniqueUserID(uniqueUserID);
		if (register != null && !(userId.equals(register.getUserId()))) {
			List<FamilyMembers> familyMembers = familyMembersRepository.findByUserId(register.getUserId());
			familyMembers.remove(familyMembersRepository.findByUniqueUserID(uniqueUserID).get(0));
			for (FamilyMembers familyMember : familyMembersList) {
				MutualConnectionResponse MutualConnectionObj = new MutualConnectionResponse();
				if (familyMembers.contains(familyMember)) {
					MutualConnectionObj.setName(familyMember.getName());
					MutualConnectionObj.setUniqueUserID(familyMember.getUniqueUserID());
					mutualConnectionResponse.add(MutualConnectionObj);
				}
			}
		}
		return mutualConnectionResponse;
	}

	/**
	 * Get relationship
	 * 
	 * @param user
	 * @param relation
	 * @param gender
	 * @return
	 */
	private FamilyRelation getPrimaryRelation(String user, String relation, String gender) {
		FamilyRelation familyRelation = new FamilyRelation();
		List<FamilyRelation> familyRelationList = familyRelationRepo
				.findByPrimaryRelationAndSecondaryRelationAndGender(user, relation, gender);
		if (familyRelationList != null && !familyRelationList.isEmpty()) {
			familyRelation = familyRelationList.get(0);
		}
		return familyRelation;
	}

	/**
	 * get the side value
	 * 
	 * @param familyMember
	 * @return
	 */
	private FamilyMembers getSideForFamilyMember(FamilyMembers familyMember) {
		if (familyMember.getSecondLevelRelation() != null) {
			if (familyMember.getSecondLevelRelation().equals("Father's Father")
					|| (familyMember.getSecondLevelRelation().equals("Father's Mother"))) {
				familyMember.setSide("father");
			} else if (familyMember.getSecondLevelRelation().equals("Mother's Father")
					|| (familyMember.getSecondLevelRelation().equals("Mother's Mother"))) {
				familyMember.setSide("mother");
			} else {
				familyMember.setSide(null);
			}
		}
		return familyMember;
	}

	/**
	 * get the relation
	 * 
	 * @param familyMember
	 * @return
	 */
	private String getRelationForFamilyMember(FamilyMembers familyMember) {
		String relation = familyMember.getFirstLevelRelation();
		if (StringUtils.isNotBlank(familyMember.getThirdLevelRelation())) {
			relation = familyMember.getThirdLevelRelation();
		} else if (StringUtils.isNotBlank(familyMember.getSecondLevelRelation())) {
			relation = familyMember.getSecondLevelRelation();
		}
		return relation;
	}
}
