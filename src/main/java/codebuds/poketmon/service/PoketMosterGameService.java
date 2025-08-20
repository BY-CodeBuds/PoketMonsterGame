package codebuds.poketmon.service;

import codebuds.poketmon.aggregate.AccountStatus;
import codebuds.poketmon.aggregate.Member;
import codebuds.poketmon.aggregate.PoketMon;
import codebuds.poketmon.repository.PoketmonRepository;

import java.util.ArrayList;
import java.util.List;

public class PoketMosterGameService {


    private final PoketmonRepository poketmonRepo = new PoketmonRepository();

    //회원 가입
    public void registMember(Member registMember) {
        int lastNo = poketmonRepo.findLastMemberNo();
        registMember.setUserNum(lastNo + 1);

        registMember.setAccountStatus(AccountStatus.ACTIVE);

        int result = poketmonRepo.registMember(registMember);

        if (result == 1) {
            System.out.println("회원가입에 성공하였습니다.");
        } else {
            System.out.println("회원가입에 실패하였습니다.");
        }
    }

    // 로그인
    public Member loginMember(int memNo) {
        Member loginMember = poketmonRepo.findMemberNumberBy(memNo);
        Member copyMember = null;

        if (loginMember != null) {
            try {
                copyMember = loginMember.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println(memNo + "번 회원은 없습니다.");
        }

        return copyMember;
    }

    // 회원 탈퇴
    public boolean deleteMember(Member removeMemer) {
        boolean flag = false;
        int result = poketmonRepo.deleteMember(removeMemer.getUserNum());
        if(result > 0) {
            System.out.println("회원님 그동안 감사했습니다.");
            flag = true;
        } else {
            System.out.println("탈퇴에 실패하셨습니다.");
        }
        return flag;
    }

    // 포켓몬 방생
    public void removeMyPoketMon(Member member, int myPoketMonIdx) {

        // 1. 레포의 정보 삭제
        // 2. 사제 후 정보를 가져와 member값 변경
        int result = poketmonRepo.removeMyPoketMon(member.getUserNum(), myPoketMonIdx);
        if(result > 0) {
            member.setMyPoketMons(poketmonRepo.loadMyPoketMon(member.getUserNum()));
            System.out.println(member.getMyPoketMons().get(myPoketMonIdx).getName() + "이 떠납니다.");
        } else {
            System.out.println(member.getMyPoketMons().get(myPoketMonIdx).getName() + "이 떠나기를 거부합니다.");
        }
    }

    //포켓몬 세부 정보 열람을 위해 포켓몬 정보 받아오기
    public PoketMon selectMyPoketMon(Member member, int myPoketMonIdx) {
        PoketMon pm = member.getMyPoketMons().get(myPoketMonIdx);
        return pm;
    }

    // 포켓몬 탐색
    public PoketMon findPoketMon() {
        List<PoketMon> pmList = poketmonRepo.findAllPoketmon();
        List<Integer> appearRateList = new ArrayList<>();
        int appearRate = 0;
        int sumAppearRate = 0;
        for(PoketMon pm : pmList) {
            appearRate = (int)(pm.getAppearRate() * 100);
            sumAppearRate += appearRate;
            appearRateList.add(appearRate);
        }

        int rand = ((int)Math.random() * 10000000) % sumAppearRate;
        
    }

    // 포켓몬 잡기 성공 or 실파
    public boolean catchPoketMon(Member member, PoketMon pm) {

    }

    // 포켓몬 잡기 후 멤버에 업데이트
    public Member updateMyPoketMon(Member member, PoketMon pm) {

    }
}
