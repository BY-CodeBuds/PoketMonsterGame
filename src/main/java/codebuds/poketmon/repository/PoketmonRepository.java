package codebuds.poketmon.repository;

import codebuds.poketmon.aggregate.AccountStatus;
import codebuds.poketmon.aggregate.Member;
import codebuds.poketmon.aggregate.PoketMon;
import codebuds.poketmon.aggregate.Skil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoketmonRepository {

    private File poketFile = new File("src/main/java/codebuds/poketmon/db/poketMonster.dat");
    private File memberFile = new File("src/main/java/codebuds/poketmon/db/member.dat");
    private List<PoketMon> poketMons = new ArrayList<>();
    private List<Member> members = new ArrayList<Member>();

    public List<Member> getMembers() {
        return members;
    }

    public List<PoketMon> getMPoketMons() {
        return poketMons;
    }

    public PoketmonRepository() {

        if (!memberFile.exists()) {
            members.add(new Member(1, "이승건", "이승건", 26, new ArrayList<>(), AccountStatus.ACTIVE));
            members.add(new Member(2, "강형규", "강형규", 34, new ArrayList<>(), AccountStatus.ACTIVE));
            members.add(new Member(3, "TESTUSER", "TESTUSER", 34, new ArrayList<>(), AccountStatus.ACTIVE));
            saveListData(1, members);
        } else {
            searchMembers();
            System.out.println(members);
        }

        if (!poketFile.exists()) {
            initPoketMonster(poketMons);
            saveListData(2, poketMons);
        } else {
            searchPoketMons();
            System.out.println(poketMons);
        }
    }

    private void initPoketMonster(List<PoketMon> poketMons) {
        poketMons.add(new PoketMon(1, "피카츄", 25, 0.45, 0.3
                , Arrays.asList(
                new Skil("백만볼트", 100),
                new Skil("몸통박치기", 50)
        )));

        poketMons.add(new PoketMon(2, "이브이", 5, 0.5, 0.2
                , Arrays.asList(
                new Skil("점프", 0),
                new Skil("몸통박치기", 30)
        )));

        poketMons.add(new PoketMon(3, "꼬부기", 10, 0.65, 0.34
                , Arrays.asList(
                new Skil("물대포", 80),
                new Skil("몸통박치기", 40)
        )));
        poketMons.add(new PoketMon(4, "파이리", 15, 0.55, 0.3
                , Arrays.asList(
                new Skil("불꽃뿜기", 40),
                new Skil("몸통박치기", 30)
        )));
    }


    // 1 : Member 파일
    // 2 : PoketMonster 파일
    private void saveListData(int fileNum, List<?> datas) {
        ObjectOutputStream os = null;
        File file = null;
        if (fileNum == 1)
            file = memberFile;
        else
            file = poketFile;
        try {
            os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

            if (fileNum == 1) {
                for (Object o : datas) {
                    os.writeObject((Member) o);
                }
            } else if (fileNum == 2) {
                for (Object o : datas) {
                    os.writeObject((PoketMon)o);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int removeMember(int id) {
        int result = 0;
        for (Member m : members) {
            if (m.getUserNum() == id) {
                m.setAccountStatus(AccountStatus.DEACTIVATED);
                result = 1;
                break;
            }
        }
        saveListData(1, members);

        return result;
    }

    public int registeMember(Member registMember) {
        members.add(registMember);
        saveListData(1, members);

        return 1;
    }

    public int findMemberNumber() {
        return members.get(members.size() - 1).getUserNum() + 1;
    }

    public int catchPoketMon(Member member, PoketMon poketMon) {
        int result = 0;

        for (Member m : members) {
            if (m.getUserNum() == member.getUserNum()) {
                m.getMyPoketMons().add(poketMon);
                result = 1;
                saveListData(1, members);
                break;
            }
        }
        return result;
    }

    public int releasePoketMon(Member member, int index) {
        int result = 0;

        for (Member m : members) {
            if (m.getUserNum() == member.getUserNum()) {
                m.getMyPoketMons().remove(index);
                result = 1;
                saveListData(1, members);
                break;
            }
        }
        return result;
    }

    public void searchMembers() {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(memberFile)));
            members.clear();
            while (true) {
                members.add((Member) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("회원 정보 읽어오기 완료.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void searchPoketMons() {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(poketFile)));
            poketMons.clear();
            while (true) {
                poketMons.add((PoketMon) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("포켓몬 읽어오기 완료.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
