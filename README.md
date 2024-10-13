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
### 1. 어드민페이지
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
  * 계정의 비밀번호는 암호화 되어 저장 되어 확인 불가하도록 구현
  * 회원의 기본 정보를 수정할 수 있음
    * 이메일, 전화번호, 주소
![회원목록](https://github.com/user-attachments/assets/8484df52-0437-4c89-aa6d-b6c72fd208b7)

#### [매니저관리]
* 인사팀, 운영팀, 물류팀 3개의 부서로 나누어 각 페이지별로 접속할 수 있는 매니저 권한을 부여함
  * 인사팀 - 관리자페이지와 물류용 모바일페이지 모드 접근 가능
  * 운영팀 - 관리자페이지 접근 가능
  * 물류팀 - 모바일페이지 접근 가능
  * 계정 조회 시 비밀번호는 암호화 하여 확인 불가하도록 구현현
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
|![발주내역](https://github.com/user-attachments/assets/90e7c158-0978-4884-8d90-4d2db43b5d9f)|![발주등록](https://github.com/user-attachments/assets/4b8d1156-d632-49fa-bbc6-55d09b75c240)||

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
* 사용자페이지에서 메인의 최상단에 들어가는 배너를 관리하는 페이지
  * 노출기간을 정해서 정해진 기간 동안 노출 되도록 구현
  * 상세페이지에서 내용 수정 및 노출/비노출 수정 가능

|배너목록/수정|배너등록|
|---|---|
|![배너](https://github.com/user-attachments/assets/ba642ac2-0f0f-435f-986b-7f8e7333579b)|![배너등록](https://github.com/user-attachments/assets/7555e6e7-a218-461e-a655-c3934a61895e)|

##### [공지사항]
* 공지사항을 관리하는 페이지
  * 등록 및 수정이 가능하며 배너와 마찬가지로 노출 기간을 정할 수 있고 노출/비노출 라디오 버튼을 통해서 수정 가능

|공지목록/수정|등록|
|---|---|
|![공지](https://github.com/user-attachments/assets/191b1bbd-5f3e-4269-b343-cc26f33f0628)|![공지등록](https://github.com/user-attachments/assets/99674382-9f95-4b8f-ac31-09d2200d4b06)|

##### [1:1 문의 관리]
* 사용자페이지에서 사용자가 1:1문의를 접수한 경우 처리할 수 있는 페이지
  * 미처리/처리완료로 답변처리 구분하도록 기능 구현
![문의](https://github.com/user-attachments/assets/16a719f2-2776-4709-bbca-c719d522a3bb)

#### [내정보]
* 로그인 되어있는 자신의 계정을 수정할 수 있는 페이지
  * 아이디는 수정 불가하며 비밀번호는 수정 가능함함
  * 주소찾기 기능으로 주소 수정 가능
![내정보](https://github.com/user-attachments/assets/ee05f63d-b41d-4bcc-9daa-72687e4ce2a6)

#### [페이지별 공통사항]
* 필터기능
  * 날짜, 라디오 버튼 등으로 구성하여 관리자가 목록을 편하게 볼 수 있는 필터기능을 구현
    * 각 필터를 적용 후 돋보기 버튼을 클릭하면 최종 결과를 보여주도록 함

* 페이징 처리
  * 목록의 갯수를 제한하고 넘어가는 경우 페이징 처리하여 관리자가 화면을 간단하게 볼 수 있도록 구현

|필터기능|페이징|
|---|---|
|![필터기능](https://github.com/user-attachments/assets/960d02ea-a4ea-4b6c-b959-50be17dcddde)|![페이징처리](https://github.com/user-attachments/assets/ba86f623-5f72-4e51-bc2a-8df79aab02ce)|


### 2. 사용자페이지
#### [로그인화면]
* 로그인 시 소셜 간편 로그인 기능을 제공
  * 카카오톡, 네이버 연동이 가능
  * 아이디/비밀번호 틀린 경우 유효성 검사를 통해 알림문구를 ID/PW INPUT창 밑에 빨간글씨로 보여줌

![소셜로그인](https://github.com/user-attachments/assets/52eed1d0-6f01-4fad-ab40-044502f0d507)

#### [회원가입]
* 회원가입 시 소셜 간편 로그인 기능 제공
  * 카카오톡, 네이버 연동
* 필수항목을 입력하지 않은 경우 가입처리 되지 않도록 유효성 검사 진행
* 이용약관을 체크하지 않은 경우 가입처리 되지 않도록 유효성 검사 진행
![소셜가입](https://github.com/user-attachments/assets/28978cd0-f975-4e1d-9d42-2382dcafc40b)

#### [메인화면]
* 최상단에서는 배너를 슬라이드로 구성하여 사용자들이 사이트의 이벤트나, 새로나온 책등을 바로 볼 수 있도록 구현
* 베스트셀러/신상품/평점높은책/추천순 으로 구성하여 사용자가 책을 한 눈에 볼 수 있도록 구현

이미지 준비중

#### [검색기능]
* 헤더의 검색창에서 상품명으로 검색 가능 (캡쳐없음)
* 카테고리 메뉴를 클릭하여 해당하는 카테고리에 따라 해당하는 상품이 나오도록 구현
* 국내도서/외국도서/베스트/신상품 메뉴로 구성 되어있고 각 항목을 클릭 시 해당하는 상품이 나오도록 구현
* 결과내검색 기능으로 검색 되어 나온 상품 중에서 입력한 내용에 맞는 상품이 나오도록 구현

|카테고리클릭1|카테고리클릭2|베스트|신상품|결과내재검색|
|---|---|---|---|---|
|![카테고리메뉴클릭국내](https://github.com/user-attachments/assets/c44bacdd-e1db-4cf7-80fe-ea88eadbda78)|![국내카테고리](https://github.com/user-attachments/assets/03f4acf5-c3e9-4e78-afdc-5f346a716bde)|![베스트](https://github.com/user-attachments/assets/5a07043e-8ef4-48ab-aea6-b9a307393035)|![신상품](https://github.com/user-attachments/assets/4750ab92-f14c-469c-a5d8-95d244c50835)|![Uploading 결과내재검색.gif…]()|

#### [로그아웃시]
* 로그아웃된 상태 즉 로그인이 되지 않은 경우 상품주문, 장바구니담기 등 기능이 제한 되도록 구현

  이미지 준비중

#### [주문]
* 상품 주문 시 카드 결제만 가능하고 토스페이먼츠 연동으로 토스와 다른 카드사 결제가 가능하도록 구현

  이미지 준비중


#### [마이페이지]
##### [취소/반품등 주문관련]
* 마이페이지에서 초기화면은 주문목록을 바로 보여주도록 구현
  * 배송전 인 경우 취소가 가능하며 전체 취소만 가능
  * 배송중에서 요청으로 바뀐 즉시 반품신청이 가능함
|반품신청|주문취소|
|---|---|
|![반품신청](https://github.com/user-attachments/assets/85eb6b40-0449-4d6d-8f5b-8f3bea336f92)|![주문취소](https://github.com/user-attachments/assets/7e179ff4-ca8c-4c85-90b7-e8d8f887808d)|


##### [정보수정, 회원탈퇴]
* 정보수정 및 탈퇴 시 비밀번호 입력 후 페이지로 접속이 가능
  * 눈 버튼을 두어 암호화 된 비밀번호를 텍스트 형태로 바꿔 문자로 볼 수 있도록 구현
  * 탈퇴 시 탈퇴사유를 라디오 버튼으로 클릭하도록 하였으며, 필수 항목 체크하지 않으면 탈퇴되지 않도록 구현

|정보수정|탈퇴|
|---|---|
|![내정보수정](https://github.com/user-attachments/assets/9eba572f-cb51-40c7-b3a0-7792a006c0ec)|![탈퇴](https://github.com/user-attachments/assets/496c634c-df6d-4ac1-a293-a65080f8aa27)|

##### [리뷰목록]
* 구매한 책이 목록으로 나오도록 하여 책에 대한 리뷰를 작성할 수 있도록 함
  * 별점은 최초 5점으로 표시 되며 사용자가 클릭하여 별점을 조절할 수 있도록 함
  * 수정/삭제 할 수 있도록 구현
  * 작성해야할 상품과 작성된 리뷰를 탭 버튼으로 표시하여 간편히 볼 수 있도록 구현함
![리뷰](https://github.com/user-attachments/assets/4e9a7f46-3eff-4c28-ab4e-3d6ecb1efdfe)

##### [1:1 문의]
* 구매한 책에 대한 1:1문의 혹은 기타 문의를 접수할 수 있는 화면
  * 탭버튼으로 전체/미답변/답변완료 목록을 나누어 볼 수 있도록 구현
  * 1:1 문의 접수 시 문의 유형을 선택하면 구매한 목록이 모달로 나오도록 함 목록은 최근 1개월 이내의 주문건만 보이도록 구현
  * 이미지를 1개 첨부할 수 있으며 선택 요소로 두었음 이메일로 답변 받아 볼 수 있도록 설계하였으나 이메일 전달은 미구현
![문의등록](https://github.com/user-attachments/assets/04d8e6de-6daa-473a-bfc6-a8b457ad4548)

### 3. 물류용 모바일
* 모바일로 기획하였기 때문에 반응형으로 작업하여 각종 기기의 해상도에 맞추어 보이도록 구현

#### [로그인화면]
* 로그인 유효성 검사로 아이디와 비밀번호가 틀리면 알림을 보여주도록 함
* 관리자페이지와 마찬가지로 직접 회원가입할 수 없으며 계정이 있는 매니저만 로그인이 가능
![모바일초기](https://github.com/user-attachments/assets/870af3e7-8404-42c2-841c-1dadc6ecdef3)

#### [홈화면]
* 홈화면은 프레임 형태의 탭 메뉴로 구성하여 입고하기/출고하기와 배송 요청 목록이 바로 보이도록 구현
 * 배송요청내역에서 요청 번호를 클릭하면 상세 내역으로 넘어가도록 함
 ![배송요청내역](https://github.com/user-attachments/assets/ff6c1845-991e-41ea-98bf-e31427139acc)

#### [입고하기]
* 창고 구역번호가 있는 버튼이 상단으로 보여지고 구역 번호를 클릭하면 해당구역에 있는 책이 보이도록 구현
* 물류팀 기사가 발주서를 스캔하거나 발주서의 발주번호를 입력하면 해당 목록이 뜨고 입고 처리 되도록 구현
  * 현재 스캔 기능은 미구현
  * 발주 번호를 입력하면 입고 처리 되도록 구현
![입고화면](https://github.com/user-attachments/assets/90edd448-1adc-4de6-948b-1a6fc81c2578)

#### [출고하기]
* 창고 구역번호가 있는 버튼이 상단으로 보여지고 구역 번호를 클릭하면 해당구역에 있는 책이 보이도록 구현
* 물류팀 기사가 택배송장을 스캔하거나 배송요청번호를 입력하면 해당 목록이 뜨고 출고 처리 되도록 구현
  * 현재 스캔 기능은 미구현
  * 배송 요청번호를 입력하면 출고 처리 되도록 구현
![출고하기](https://github.com/user-attachments/assets/8000aaa3-90e0-4197-b181-d8261a05dfa8)

#### [배송상황]
* 배송요청 건들에 대한 배송상황목록이 있는 화면
* 관리자페이지에서 배송요청을 받은 목록을 출고처리 한 후 배송중/배송완료로 확인할 수 있도록 구현
![배송상황](https://github.com/user-attachments/assets/7f019bf8-7051-4ea1-90ae-feb7113932e7)


#### [내정보수정]
* 접속한 계정의 정보를 수정할 수 있으며 비밀번호를 입력하지 않아도 다른 내용은 수정 가능하도록 구현
  
![내정보수정](https://github.com/user-attachments/assets/8fe01c02-4b15-46b3-a1d8-c8c1ca7fb5cb)
