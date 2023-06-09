package Hotel_Project;

import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Guest {
    String name;
    String phoneNumber;
    String date;
    int money;


    public Guest(){}

    public Guest( String name, String phoneNumber, String date, int money) {
    
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.money = money;
    }
    /////////////////////////// 호진님 구현부 /////////////////////////
    public Guest inputGuest() {
        // name, phoneNumber, money, date를 스캐너로 입력 받아 그 특성을 가지는 Guest를 return.
        Scanner sc = new Scanner( System.in );
        System.out.println("======== 예약하실 분의 성함을 입력해주세요. ========");
        System.out.println();
//        String name = sc.nextLine();
        String name = "happy";  // 입력 귀찮아서

        System.out.println();
        System.out.println("======== 전화번호를 입력해주세요. ========");
        System.out.println("(연락처는 '-'구분없이 숫자만 입력해주세요.)");
        System.out.println();
//        String phoneNumber = sc.nextLine();
        String phoneNumber = "01066139639";

        System.out.println();
        System.out.println("======== 소지하고 계신 금액을 입력해주세요. ========");
        System.out.println("(소지금의 단위는 '원'입니다. '원'을 제외하고 숫자만 입력해주세요.)");
        System.out.println();
//        int money = sc.nextInt();
        int money = 201;

        System.out.println();
        System.out.println("======== 원하시는 예약 날짜를 입력해주세요. ========");
        System.out.println("(yyyy/mm/dd의 형식으로 입력해주세요.)");
        System.out.println();
//        String date = sc.next();
        String date = "2023/06/07";

        System.out.println();
        System.out.println("======== 예약하시려는 분의 정보를 확인해주세요. ========");
        System.out.println();
        System.out.println("이름" + ":" + " " + name);
        System.out.println("전화번호" + ":" + " " + phoneNumber);
        System.out.println("소지금" + ":" + " " + money + "원");
        System.out.println("예약일" + ":" + " " + date);
        System.out.println();
        System.out.println("======== 확인하신 내용이 맞다면 1번, 다시 시도하시려면 2번을 입력해주세요. ========");
        System.out.println();

        int confirm = sc.nextInt();

        if (confirm==1) {
            System.out.println();
            System.out.println("======== 예약자 정보 확인 ========");
            System.out.println();
            System.out.println("==== 예약정보 ====");
            System.out.println("이름" + ":" + " " + name);
            System.out.println("전화번호" + ":" + " " + phoneNumber);
            System.out.println("소지금" + ":" + " " + money + "원");
            System.out.println("예약일" + ":" + " " + date );
            System.out.println();
            Guest guest = new Guest( name, phoneNumber, date, money);
            return guest;

        } else if (confirm==2) {
            System.out.println();
            System.out.println("======== 올바른 정보를 다시 입력해주세요. ========");
            return inputGuest();

        } else {
            System.out.println();
            System.out.println("======== 잘못 입력하셨습니다. 메인화면으로 돌아갑니다. ========");
            System.out.println();
        }
        return null;
    }

    // date 예약날짜를 개인정보 확인 후 입력하려다가 실패했습니다 ㅠ
    /*public Guest inputDate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("======== 원하시는 예약 날짜를 입력해주세요. ========");
        String date = sc.nextLine();
        System.out.println("======== 화면에 보이는 예약 날짜를 확인해주세요. ========");
        System.out.println("예약일" + ":" + date);
        System.out.println("======== 확인하신 내용이 맞다면 1번, 다시 시도하시려면 2번을 입력해주세요. ========");
        int confirmDate = sc.nextInt();
        if (confirmDate == 1) {
            System.out.println("======== 예약이 완료되었습니다. 이용해주셔서 감사합니다. ========");
            System.out.println();
            System.out.println("이름" + ":" + " " + name);
            System.out.println("전화번호" + ":" + " " + phoneNumber);
            System.out.println("소지금" + ":" + " " + money + "원");
            System.out.println("예약일" + ":" + date);
            Guest guest = new Guest(name, phoneNumber, money, date);
            return guest;
        } else if (confirmDate == 2) {
            System.out.println("======== 원하시는 날짜를 다시 입력해주세요 ========");
            return inputDate();
        } else {
            System.out.println("======== 잘못 입력하셨습니다. 다시 시도해주세요. ========");
            return inputDate();
        }
    }*/
}
