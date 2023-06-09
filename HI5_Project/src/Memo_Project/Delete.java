package Memo_Project;

import java.util.Scanner;

public class Delete {

    Scanner sc = new Scanner( System.in );

    // number를 받아서 map의 keyset에 number가 있으면 checkPassword() 호출 없으면 notContain() 메서드 호출
    public void delete() {

        System.out.println( "삭제할 글의 번호를 입력해 주세요." );
        int number = sc.nextInt();
        String enter = sc.nextLine();

        if ( !Memo.mapMemo.keySet().contains( number ) ) {
            notContain();
        } else {
            checkPassword( number );
        }

    }

    // num에 관한 글이 존재하지 않을 때 메인 화면으로 돌아가는 메서드
    private void notContain() {

        System.out.println( "삭제할 글이 존재하지 않습니다." );
        System.out.println( "메인 화면으로 돌아가시려면 1번, 다시 번호를 입력하시려면 2번을 입력해 주세요." );

        int n = sc.nextInt();
        String enter = sc.nextLine();
        if ( n == 1 ) {
        } else if ( n == 2 ) {
            delete();
        } else {
            System.out.println( "잘못 선택하셨습니다. 다시 선택해 주세요.\n" );
            notContain();
        }

    }

    // 비밀번호를 체크해서 맞으면 메모를 삭제해주는 메서드
    private void checkPassword( int number ) {
        System.out.println( "비밀번호를 입력해 주세요." );

        String pw = sc.nextLine();
        if ( !Memo.mapPassword.get( number ).equals( pw ) ) {
            System.out.println( "비밀번호가 일치하지 않습니다." );
            System.out.println( "메인 화면으로 돌아가시려면 1번, 다시 비밀번호를 입력하시려면 2번을 입력해 주세요." );

            if ( !sc.hasNextInt() ) {
                System.out.println( "잘못된 값을 입력하셨습니다. 다시 선택해 주세요.\n" );
                String wrongValue = sc.next();
                checkPassword( number );
            }

            int n = sc.nextInt();
            if ( n == 1 ) {
                System.out.println( "메인 화면으로 돌아갑니다.\n" );
            } else if ( n == 2 ) {
                checkPassword( number );
            } else {
                System.out.println( "잘못 선택하셨습니다." );
                System.out.println( "삭제 메뉴 첫 화면으로 돌아갑니다." );
                delete();
            }

        } else {

            Memo.mapPassword.remove( number );
            Memo.mapMemo.remove( number );
            System.out.println( "메모가 삭제되었습니다. 첫 화면으로 돌아갑니다." );

        }


    }
}
