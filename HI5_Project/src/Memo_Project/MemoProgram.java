package Memo_Project;

import java.util.Scanner;

public class MemoProgram {
    private Delete delete = new Delete();
    private Amend amend = new Amend();
    private Input input = new Input();
    private MemoList memoList = new MemoList();
    Scanner sc = new Scanner( System.in );

    public void onMemo() {
        System.out.println( "원하시는 옵션을 선택해 주세요.\n" );
        System.out.println( "1. 입력" );
        System.out.println( "2. 메모 목록 조회" );
        System.out.println( "3. 수정" );
        System.out.println( "4. 삭제" );
        System.out.println( "5. 종료" );
        if ( !sc.hasNextInt() ) {
            System.out.println( "잘못된 값을 입력하셨습니다. 다시 선택해 주세요.\n" );
            String wrongValue = sc.next();
            onMemo();
        }
        int n = sc.nextInt();
        switch ( n ) {
            case 1: {
                inputScreen();
                break;
            }
            case 2: {
                printAllNotes();
                break;
            }
            case 3: {
                amendScreen();
                break;
            }
            case 4: {
                deleteScreen();
                break;
            }
            case 5: {
                offMemo();
                break;
            }
            default: {
                System.out.println( "잘못된 선택입니다. 다시 선택해 주세요.\n" );
                onMemo();
                break;
            }

        }
    }

    private void inputScreen() {
        Memo memo = input.input();
        memoList.save( memo );
        System.out.println( "메모 저장이 완료되었습니다!" );
        Input.number++;
        onMemo();
    }

    private void printAllNotes() {
        memoList.getMemoList();
        onMemo();
    }

    private void amendScreen() {
        amend.amend();
        onMemo();
    }

    private void deleteScreen() {
        delete.delete();
        onMemo();
    }

    private void offMemo() {
        System.out.println( "메모장을 종료합니다." );
    }

}
