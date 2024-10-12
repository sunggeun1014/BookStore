# WHALEBOOKS - 온라인서점 사이트
![리드미메인](https://github.com/user-attachments/assets/1e3b59ad-7091-407a-bb59-6abcc6012a96)

## 프로젝트 소개
‘WHALE BOOKS’는 사용자와 관리자가 편리하게 이용할 수 있는 서점 웹사이트 프로젝트입니다. <br>
이 프로젝트는 Spring Boot 기반으로 개발되었으며, 사용자 페이지와 관리자 페이지를 통해 책 검색, 구매, 주문 관리 및 물류 관리를 효율적으로 처리할 수 있습니다. <br>
또한 관리자의 물류 업무를 지원하기 위해 모바일 전용 관리자 페이지를 구현하여 언제 어디서나 재고와 배송 상태를 관리할 수 있도록 구현하였습니다.<br>

## 팀원구성
|손성근|김도현|홍기성|김시은|이혜진|
|:---------:|:---------:|:---------:|:---------:|:---------:|
|![KakaoTalk_20241008_145427675](https://github.com/user-attachments/assets/72d8e23a-4df0-42bd-899e-37e8ec4b42aa)|![KakaoTalk_20241008_144838253](https://github.com/user-attachments/assets/5c9aed47-b9f5-4221-a32f-877291e6d8e4)|![KakaoTalk_20241008_145214931](https://github.com/user-attachments/assets/0de20b8a-92b2-41d1-9abd-2dfe91b5cc58)|![KakaoTalk_20241008_145121507](https://github.com/user-attachments/assets/f47c532e-abc5-477d-8710-2b2c17d8cb00)|![KakaoTalk_20241008_150542200](https://github.com/user-attachments/assets/04e54234-4361-413a-b2dd-190258f94048)|


## 개발환경
Front-End: css, javascript, JQuery<br>
Back-End: Spring Boot<br>
라이브러리: My Batis, Thymeleaf, Data Tables, Swiper Slide<br>
API: 네이버, 카카오 로그인연동, 주소찾기, Iamport 결제<br>
DB: 오라클<br>
버전 및 이슈관리 : Github, sourcetree<br>
협업 툴 : Discord, Notion<br>


## 개발 기간
* 전체 개발 기간 : 2024-08-15 ~ 2024-10-04
* 기획 회의 : 2024-08-15 ~ 2024-08-26
* UI 구현 및 기능 구현 : 2024-08-27 ~ 2024-10-04
* 개발 과정
  * 어드민페이지 > 사용자 페이지 > 물류용 모바일
  * 반복되는 레이아웃, 각종 button 및 input, select 등의 공통 부분을 함께 쓸 수 있도록 작업
  * 공통 함수를 만들어 공통 부분은 함께 사용할 수 있는 코드 작업

## 페이지별 기능
### [초기페이지]
### 1. 어드민페이지
* 관리자페이지는 회원가입 기능은 없으며, 매니저 권한이 있는 계정에서 매니저 등록을 통해 아이디를 부여 받을 수 있음
* 로그인시 아이디나 비밀번호가 틀린 경우 유효성 검사를 진행하도록 함
![새로운 프로젝트](https://github.com/user-attachments/assets/7e1e0272-5766-4c11-9d69-eec266055ed1)

### 2. 사용자페이지
* 사용자 페이지에서 회원가입시 소셜 간편 로그인 기능을 제공
  * 카카오톡, 네이버 연동이 가능 


