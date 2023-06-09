package Memo_Project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Input {
    LocalDate now = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern( "yyyy/MM/dd" );
    private String date = now.format( dateFormatter );
    Scanner sc = new Scanner( System.in );
    static int number = 1;

    public Memo input() {
        System.out.println( "이름, 비밀번호, 메모를 입력해 주세요." );
        String name = sc.nextLine();
        String password = sc.nextLine();
        String note = sc.nextLine();
        return checkInput( name, password, note );
    }

    private Memo checkInput( String name, String password, String note ) {
        System.out.println( "이름 : " + name );
        System.out.println( "비밀번호 : " + password );
        System.out.println( "메모 : " + note );
        System.out.println( "입력값이 올바르면 1번, 다시 입력하시려면 2번을 입력해 주세요." );

        int n = sc.nextInt();
        String enter = sc.nextLine();
        if ( n == 1 ) {
            return new Memo( number, name, password, note, date );
        } else if ( n == 2 ) {
            return input();
        } else {
            System.out.println( "잘못된 값을 입력하셨습니다. 다시 입력해 주세요." );
            return checkInput( name, password, note );
        }
    }

}
