package Hotel_Project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Guest {
    String name;
    String phoneNumber;
    int money;


    public Guest() {
    }

    public Guest( String name, String phoneNumber, int money ) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.money = money;
    }

    public Guest inputName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("======== 예약하실 분의 성함을 입력해주세요. ========");
        System.out.println();
        boolean hasNext = sc.hasNext();
        this.name = sc.nextLine();
            if (hasNext == true) {
                return inputPhonenumber();
            } else {
                System.out.println("잘못 입력하셨습니다. 올바른 성함을 입력해주세요.");
                return inputName();
            }
    }

    private Guest inputPhonenumber() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println( "======== 전화번호를 입력해주세요. ========" );
        System.out.println( "(연락처는 '-'구분없이 숫자만 입력해주세요.)" );
        System.out.println();
        boolean phonenumberConfirm = sc.hasNextInt();
        this.phoneNumber = sc.nextLine();
            if (phonenumberConfirm == true) {
                return inputMoney();
            } else {
                System.out.println("잘못 입력하셨습니다. 올바른 전화번호를 입력해주세요.");
                return inputPhonenumber();
            }
    }

    private Guest inputMoney() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println( "======== 소지하고 계신 금액을 입력해주세요. ========" );
        System.out.println( "(소지금의 단위는 '원'입니다. '원'을 제외하고 숫자만 입력해주세요.)" );
        System.out.println();
        boolean moneyConfirm = sc.hasNextInt();
        this.money = sc.nextInt();
            if (moneyConfirm == true) {
                return inputConfirm();
            } else {
                System.out.println("잘못 입력하셨습니다. 올바른 금액을 입력해주세요.");
                return inputMoney();
            }
    }

    private Guest inputConfirm() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println( "======== 예약하시려는 분의 정보를 확인해주세요. ========" );
        System.out.println();
        System.out.println("이름" + ":" + " " + this.name);
        System.out.println("전화번호" + ":" + " " + this.phoneNumber);
        System.out.println("소지금" + ":" + " " + this.money + "원");
        System.out.println();
        System.out.println( "======== 확인하신 내용이 맞다면 1번, 다시 시도하시려면 2번을 입력해주세요. ========" );
        System.out.println();

        int confirm = sc.nextInt();

        if ( confirm == 1 ) {
            System.out.println();
            System.out.println( "======== 예약자 정보 확인 ========" );
            System.out.println();
            System.out.println("==== 예약정보 ====");
            System.out.println("이름" + ":" + " " + this.name);
            System.out.println("전화번호" + ":" + " " + this.phoneNumber);
            System.out.println("소지금" + ":" + " " + this.money + "원");
            System.out.println();
            Guest guest = new Guest( this.name, this.phoneNumber, this.money);
            return guest;

        } else if ( confirm == 2 ) {
            System.out.println();
            System.out.println("======== 올바른 정보를 다시 입력해주세요. ========");
            return inputName();

        } else {
            System.out.println();
            System.out.println( "======== 잘못 입력하셨습니다. 메인화면으로 돌아갑니다. ========" );
            System.out.println();
        }
        return null;
    }
}
