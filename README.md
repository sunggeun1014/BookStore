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
### 어드민페이지
#### [로그인화면]
* 관리자페이지는 회원가입 기능은 없으며, 매니저 권한이 있는 계정에서 매니저 등록을 통해 아이디를 부여 받을 수 있음
* 로그인시 아이디나 비밀번호가 틀린 경우 유효성 검사를 진행하도록 함
![로그인검사](https://github.com/user-attachments/assets/a60d378c-c9d0-4a30-acda-894a08e5386d)

#### [홈화면]
* 로그인 후 바로 보이는 홈화면은 대쉬보드 형태로 주문, 매출, 배송 내역을 요약 형태로 볼 수 있도록 구현
![초기화면](https://github.com/user-attachments/assets/c498e70a-ad56-4e69-b3bf-19109e0e8c24)

#### [회원관리]
* 사용자페이에서 가입한 회원의 목록이 보이는 페이지로 구성 됨
  * 소셜 로그인을 했을 경우 버튼으로 표시 되도록 구현
  * 회원의 기본 정보를 수정할 수 있음
    * 이메일, 전화번호, 주소
![회원목록](https://github.com/user-attachments/assets/8484df52-0437-4c89-aa6d-b6c72fd208b7)

#### [매니저관리]
* 인사팀, 운영팀, 물류팀 3개의 부서로 나누어 각 페이지별로 접속할 수 있는 매니저 권한을 부여함
  * 인사팀 - 관리자페이지와 물류용 모바일페이지 모드 접근 가능
  * 운영팀 - 관리자페이지 접근 가능
  * 물류팀 - 모바일페이지 접근 가능
![매니저관리](https://github.com/user-attachments/assets/35b2f588-0b46-4df3-9662-4c7994be9560)

#### [상품관리]
* 사용자페이지에 판매 상품으로 전시 될 상품관리화면
  * 상품 등록 및 수정 시 사용자 페이지에서 상품상세화면에 들어갈 모든 항목에 유효성 검사를 통하여 입력되지 않았을 경우 넘어가지 않도록 구현
  * 상품 등록시 중복 상품인 경우 모달창으로 알림 구현
  * 재고에 없는 상품을 등록할 경우 모달창으로 알림 구현

|등록중복|재고없음|유효성검사|상품수정|
|---|---|---|---|
|![상품등록중복](https://github.com/user-attachments/assets/b8993499-e6e6-4976-a9fa-d7d572ffb92e)|![상품등록재고없음](https://github.com/user-attachments/assets/0fc1c1d9-4287-4375-bc08-a28c628d25c1)|![상품등록유효성검사](https://github.com/user-attachments/assets/7bd5714f-be8c-4e45-a4b1-5087d23e9881)||

#### [주문관리]
* 사용자페이지에서 주문이 들어오면 목록에 주문건이 최신순으로 나타남
  * 주문이 들어오면 물류용 모바일로 배송요청을 보내도록 구현
  * 사용자가 주문을 취소/반품/교환 요청할 경우 처리할 수 있음

|주문목록|배송요청|요청처리|
|---|---|---|
||![주문-배송요청](https://github.com/user-attachments/assets/aae5b378-30ba-4178-9973-793de8705937)||

#### [창고관리]
* 창고관리메뉴는 창고에 관련된 메뉴로 구성 되어있음
  * 발주내역
  * 입출고내역
  * 창고재고조회

##### [발주내역]
* 관리자가 발주한 내역을 등록하고 목록으로 볼 수 있음
|발주목록|등록|
|---|---|
|![발주내역](https://github.com/user-attachments/assets/90e7c158-0978-4884-8d90-4d2db43b5d9f)|![발주등록](https://github.com/user-attachments/assets/4b8d1156-d632-49fa-bbc6-55d09b75c240)|

##### [입출고내역]
* 물류용 모바일에서 발주/반품 혹은 상품 출고 처리 한 경우의 내역을 목록으로 확인 가능 하도록 구현
![입출고내역](https://github.com/user-attachments/assets/4d9019e4-ffef-4acc-b086-ec12c56c0e2c)


##### [창고재고조회]
* 물류용 모바일의 물류팀 관리자가 창고에 입고처리를 하면 입고된 구역번호와 함께 목록을 조회할 수 있도록 구현
  * 물류팀에서 입고처리에 오류가 있을 경우 관리자가 직접 수정할 수 있는 기능 구현

![창고내역](https://github.com/user-attachments/assets/2026bd82-fd3d-4941-90c6-b9bb9fafb8ee)

#### [홈페이지관리]
* 사용자페이지에 보여지는 내용과 사용자 페이지에서 들어온 요청을 처리할 수 있는 메뉴
  * 배너관리
  * 공지사항
  * 리뷰관리
  * 1:1문의

##### [배너관리]
* 사용자페이지에서 메인의 최상단에 들어가는 배너를 등록할 수 있음
  * 노출기간을 정해서 정해진 기간 동안 노출 되도록 구현
  * 노출/비노출을 라디오 버튼을 통해서 수정할 수 있음



### 2. 사용자페이지
* 사용자 페이지에서 회원가입시 소셜 간편 로그인 기능을 제공
  * 카카오톡, 네이버 연동이 가능 


