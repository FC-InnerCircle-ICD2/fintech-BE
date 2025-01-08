# 온라인 결제 게이트웨이(PG; Payment Gateway) 서비스
------------------------------------------------------------
# Event Storming
- link : https://www.figma.com/board/KsdD1z3Od0EYO8874620RK/pay200---%EA%B5%AC%EC%83%81?node-id=0-1&p=f&t=gNBMKBiK5xxnu8Hj-0
### SDK-PAY
![image](https://github.com/user-attachments/assets/119f4367-44ef-4905-b3a7-a9860cf80d06)

### Back Office
![image](https://github.com/user-attachments/assets/15b21ad8-01d0-48a3-bc31-74e54fa14eca)

# Tech Stack
- Language: Kotlin 2.1.0
- JDK 21
- Spring Boot 3.4.1
- Persistence: Spring Data JPA / QueryDSL
- DB: PostgreSQL 17.2
- TEST: JUnit5

# Functional Requirements
- link : https://docs.google.com/spreadsheets/d/1OpYLTSNVemzl-xj_GcnNzitr_VbxNKT7nwokU6iSWLI/edit?gid=1999736551#gid=1999736551
- 공통
  - 결제 실패 및 오류 처리
    - 결제 과정에서 발생할 수 있는 다양한 실패 시나리오(예: 네트워크 오류, 결제 거절 등)를 처리
    - 재시도 로직을 구현하여 사용자 경험을 개선 필요
  - 통합 및 확장 가능성
    - 새로운 결제 수단이나 기능의 빠른 추가를 위한 모듈화된 시스템을 설계	
  - 성능 최적화 및 확장성
    - 높은 트랜잭션 처리량을 위해 결제 처리 시스템의 성능을 최적화
    - 수요 증가에 따라 시스템이 확장될 수 있도록 설계
    - 로드 밸런싱, 캐싱, 그리고 분산 처리 시스템을 고려	

- 결제 처리 시스템						"						
  - 다양한 결제 수단을 지원하는 결제 처리 기능을 구현
  - 고도화 전에는 카드만 지원

- 결제 트랜잭션 관리						
  - 거래 승인, 취소, 환불 등의 거래 상태를 실시간으로 관리하고 추적 가능

- 결제 SDK 제공					
  - 외부 서비스에서 이 PG 서비스를 사용할 수 있는 결제 SDK를 제공
  - 실시간 처리와 API 엔드포인트가 필요
  - 웹사이트에서 해당 SDK를 로드하고 함수 등으로 호출하여 사용 가능(카카오맵 SDK 참조).
  - 결제 완료 또는 실패 이벤트를 제공
  - API 키가 유효하지 않으면 오류를 발생

- 사용자 모바일 앱		
  - QR 코드를 읽어서 결제하는 모바일 애플리케이션
  - 고도화 전에는 QR 결제 / 사용 내역 두 개의 메뉴만 존재
  - QR 코드를 읽은 후 [확인]과 [취소]가 가능
  - [취소]를 누르면 구매가 취소

- 백오피스
  - 사업자가 로그인하여 결제 내역을 확인 가능
  - 결제 내역을 이름, 날짜, 종류(결제, 취소, 환불)로 필터링이 가능
  - 복수의 사업자가 사용할 수 있다고 가정하며, 고도화 전에는 데스크톱 환경만 고려한다.
  - 발급된 API키는 다른 사람과 중복 불가능하게 구현
  - 다음번 발급될 API키가 예측 불가능하게 구현
  - 사업자가 API 키를 발급받고 갱신할 수 있는 기능을 포함		

# API
- https://www.notion.so/API-165d5beeb0c0812cabd6d577244a31e0

# API Detail
- https://www.notion.so/BE-NOTION-DATABASE-9807bd08faab44c199f7337f4cfecae4

# Frontend Reference
- SDK Design : https://www.notion.so/SDK-c270cecc35764c41bd16ecbd62c18af0#9e7470955abd4c56a6138e80a1dff326
- APP Design : https://www.notion.so/App-3bf848fc0de04765bfd215f5071e1bfc
- API detail : https://www.notion.so/API-7f8209aaea39441791cd4acff46dc2fe
