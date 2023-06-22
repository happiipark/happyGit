스프링을 처음 접해봐서 과제 수행이 쉽진 않았을텐데 마지막까지 열심히 완료해주시느라 수고 많으셨습니다.

우선 Readme에 api 명세부분을 깔끔하게 잘 정리해주셔서 전체적인 설계가 보여서 좋았습니다. 

restful api 설계도 방향성이 좋았지만 note보다는 게시글을 나타낼 수 있는 단어를 선택하셨으면 더 좋았을 것 같습니다. 

ex) posts. restful api에 대한 설계 규칙이 있는데 해당 글 참조해보시고 다음 과제때부터 적용해보면 좋을 것 같아요~

https://khj93.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-REST-API%EB%9E%80-REST-RESTful%EC%9D%B4%EB%9E%80

controller, entity, dto를 잘 분리해주셔서 읽기 좋았습니다. 

개인과제 예시 답안을 보시면 알겠지만 보통 메인 로직은 controller 단에서 하지않고 service 단에서 하는 것이 일반적입니다. 

service단에서 비즈니스 로직을 작성해야 데이터 입출력을 담당하는 controller 단과 분리가 되어서 이 어플리케이션에서 하고자 하는일을 좀 더 명확히 알 수가 있어서 다음부터는 이부분을 중점적으로 분리해서 로직을 작성해주시면 좋을 것 같아요.

repository 로직을 jdbcTemplate을 이용해서 작성해주셨는데 이렇게 작성해도 되지만 보통 현업에서는 객체지향 개념을 좀 더 잘 나타내기 위해 JPA를 쓰고 있는데 이 부분도 예시답안을 보면서 작성해보시면 좋을 것 같아요~. 

그래도 jdbcTemplate도 처음 사용해보셨을텐데 잘 작성해주신 것 같네요. 다음부터는 Controller, Service, Repository로 로직들을 분리해서 좀 더 각 layer에서 하고자 하는일을 명확히 알 수 있도록 작성해주시면 좋을 것 같습니다.

DTO와 Entity를 분리해주신 것은 좋았습니다. 보통 request, response를 다룰때는 엔티티를 반환하지 않고 DTO로 분리해서 작성하는데 잘 작성해주셔서 좋네요. 리뷰한 내용을 바탕으로 다음 과제때는 해당 부분을 반영해주시면 훨씬 더 좋을 것 같습니다!
https://khj93.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-REST-API%EB%9E%80-REST-RESTful%EC%9D%B4%EB%9E%80
