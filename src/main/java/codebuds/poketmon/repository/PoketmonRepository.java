package codebuds.poketmon.repository;

import codebuds.poketmon.aggregate.AccountStatus;
import codebuds.poketmon.aggregate.Member;
import codebuds.poketmon.aggregate.PoketMon;
import codebuds.poketmon.aggregate.Skil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoketmonRepository implements Serializable {

    private File poketFile = new File("src/main/java/codebuds/poketmon/db/poketMonster.dat");
    private File memberFile = new File("src/main/java/codebuds/poketmon/db/member.dat");
    private List<PoketMon> poketMons = new ArrayList<>();
    private List<Member> members = new ArrayList<Member>();

    public PoketmonRepository() {

        if (!memberFile.exists()) {
            members.add(new Member(1, "이승건", "이승건", 26, new ArrayList<>(), AccountStatus.ACTIVE));
            members.add(new Member(2, "강형규", "강형규", 34, new ArrayList<>(), AccountStatus.ACTIVE));
            saveListData(1, members);
        }

        if (!poketFile.exists()) {
            initPoketMonster(poketMons);
            saveListData(2, poketMons);
        }
    }

    private void initPoketMonster(List<PoketMon> poketMons) {
        poketMons.add(new PoketMon(1, "피카츄", 25, 0.45, 0.3
                , new ArrayList<Skil>() {{
            add(new Skil("백만볼트", 100));
            add(new Skil("몸통박치기", 50));
        }}
        ));
        poketMons.add(new PoketMon(1, "이브이", 5, 0.5, 0.2
                , new ArrayList<Skil>() {{
            add(new Skil("점프", 0));
            add(new Skil("몸통박치기", 30));
        }}
        ));
        poketMons.add(new PoketMon(1, "꼬부기", 10, 0.65, 0.34
                , new ArrayList<Skil>() {{
            add(new Skil("물대포", 80));
            add(new Skil("몸통박치기", 40));
        }}
        ));
        poketMons.add(new PoketMon(1, "파이리", 15, 0.55, 0.3
                , new ArrayList<Skil>() {{
            add(new Skil("불꽃뿜기", 40));
            add(new Skil("몸통박치기", 30));
        }}
        ));
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
                    os.writeObject((PoketMon) o);
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
            if(m.getUserNum() == id){
                m.setAccountStatus(AccountStatus.DEACTIVATED);
                result = 1;
                break;
            }
        }

        return result;
    }
}
