package codebuds.poketmon.run;

import codebuds.poketmon.aggregate.Member;
import codebuds.poketmon.aggregate.PoketMon;
import codebuds.poketmon.service.PoketMosterGameService;

import java.util.Scanner;

public class Application {

    private static final PoketMosterGameService gs = new PoketMosterGameService();

    public static void main(String[] args) {

        Member loginMember = null;
        Scanner sc = new Scanner(System.in);
        while (true) {
            int input;  // 입력을 받을 변수

            if (loginMember == null) { // 로그인 전 화면
                System.out.println("===== 포켓몬 게임 =====");
                System.out.println("1. 회원 가입");
                System.out.println("2. 로그인");
                System.out.println("9. 게임 종료");

                System.out.print("메뉴를 선택해주세요: ");
                input = sc.nextInt();
                switch (input) {
                    case 1: // 회원 가입
                        gs.registMember(signup());
                        break;
                    case 2: // 로그인
                        loginMember = gs.loginMember(chooseMemNo());
                        break;
                    case 9: // 게임 종료
                        System.out.println("게임을 종료합니다.");
                        return;
                    default:// 잘못된 입력
                        System.out.println(input + "은 잘못된 입력입니다.");
                }
            } else { // 로그인 후 화면
                System.out.println("===== 포켓몬 게임 =====");
                System.out.println("1. 게임 시작");
                System.out.println("2. 회원 탈퇴");
                System.out.println("3. 로그아웃");
                System.out.println("9. 게임 종료");

                System.out.print("메뉴를 선택해주세요: ");
                input = sc.nextInt();
                switch (input) {
                    case 1: // 게임 시작
                        gameStart(loginMember);
                        break;
                    case 2: // 회원 탈퇴
                        if(gs.deleteMember(loginMember)) loginMember = null;
                        break;
                    case 3: // 로그아웃
                        loginMember = null;
                        break;
                    case 9: // 게임 종료
                        System.out.println("게임을 종료합니다.");
                        return;
                    default:// 잘못된 입력
                        System.out.println(input + "은 잘못된 입력입니다.");
                }
            }

        }

    }

    // 추후 유효한 번호인지 확인 필요 or 로그인 시 사용하므로 id pw 입력으로 변경
    private static int chooseMemNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("해당 회원 번호를 입력하세요: ");
        return sc.nextInt();
    }

    // 추후 회원가입 시 사용하므로 겹치는 ID가 있는지 확인 필요
    private static Member signup() {
        Scanner sc = new Scanner(System.in);
        Member member = null;

        System.out.print("ID를 입력해주세요: ");
        String name = sc.nextLine();

        System.out.print("PW를 입력해주세요: ");
        String pw = sc.nextLine();

        System.out.print("나이를 입력해주세요: ");
        int age = sc.nextInt();

        member = new Member(name, pw, age);

        return member;
    }

    //게임 화면
    private static void gameStart(Member member) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== 포켓몬 게임 =====");
            System.out.println("1. 포켓몬 찾기(탐색)");
            System.out.println("2. 내 포켓몬 목록");
            System.out.println("9. 메인 메뉴 돌아가기");

            System.out.print("메뉴를 선택해주세요: ");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    PoketMon pm = gs.findPoketMon();    // 포켓몬 탐색
                    if (pm != null) battleScreen(member,pm);      // 포켓몬을 찾았으면 -> 전투 화면
                case 2:                                 // 내 포켓몬 목록 보기
                    selectMyPoketMon(member);           // 포켓몬 목록용 화면
                case 9:
                    System.out.println("메인 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println(input + "은 잘못된 입력입니다.");
            }

        }
    }

    // 전투 화면
    private static void battleScreen(Member member,PoketMon pm) {
        Scanner sc = new Scanner(System.in);
        int count = 0;  // 몬스터볼을 던진 횟수 체크해서 일정치 넘어가면 종료
        boolean isCatch = false;
        while (true) {
            System.out.println("==== 전투 ====");
            System.out.println("1. 몬스터볼 투척");
            System.out.println("2. 도망가기");

            System.out.print("메뉴를 선택해주세요: ");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    System.out.println("몬스터 볼을 던집니다.");
                    isCatch = gs.catchPoketMon(member, pm);     // 몬스터 볼 투척해서 성공 or 실패
                    count++;
                    break;
                case 2:
                    System.out.println(pm.getName() + "으로부터 도망칩니다.");
                    return;
                default:
                    System.out.println(input + "은 잘못된 입력입니다.");
            }

            if (isCatch == true) {
                System.out.println(pm.getName() + "을 성공적으로 잡았습니다.");
                return;
            }
            if (count == 3) {
                System.out.println("몬스터볼을 모두 소진했습니다.");
                return;
            }
        }

    }

    //내 포켓몬 목록 화면
    private static void selectMyPoketMon(Member member) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("==== 내 포멧몬 목록 ====");
            for (int i = 0; i < member.getMyPoketMons().size(); i++) {        // 내 포켓몬 길이
                System.out.println((i + 1) + ". " + member.getMyPoketMons().get(i));
            }
            System.out.println("==== 내 포켓몬 목록 ====");

            System.out.println("1. 포켓몬 방생");
            System.out.println("2. 포켓몬 세부 정보 열람");
            System.out.println("3. 돌아가기");

            System.out.print("메뉴를 선택해주세요: ");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    System.out.print("방생할 포켓몬의 번호를 입력하세요: ");
                    input = sc.nextInt();
                    gs.removeMyPoketMon(member, input - 1);    // 변경된 정보를 member에 적용해야 하므로
                    break;
                case 2:
                    System.out.println("열람할 포켓몬의 번호를 입력하세요: ");
                    input = sc.nextInt();
                    PoketMon p = gs.selectMyPoketMon(member, input - 1);
                    System.out.println(p.toString());
                case 3:
                    System.out.println("게임 화면으로 돌아갑니다.");
                    return;
                default:
                    System.out.println(input + "은 잘못된 입력입니다.");
            }
        }
    }
}
