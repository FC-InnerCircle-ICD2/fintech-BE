# 온라인 결제 게이트웨이(PG; Payment Gateway) 서비스
------------------------------------------------------------
# Process

### Payment Flow

### Api

### SSE Flow

# How to run SDK & App

1. PC에 node.js 설치

2. git clone https://github.com/FC-InnerCircle-ICD2/fintech-FE-SDK 수행

3. fintech-FE-SDK 폴더로 진입 후 npm install
   (yarn 있으신 분들은 yarn install 권장)

4. npm run dev 또는 yarn dev 실행

5. localhost:5173 접속

6. 결제하기 버튼 누르면 QR 코드 생성됨 (3초 후에 사라짐) 

7. 모바일 버전은 개발자도구에서 모바일 버전으로 변환 후 새로고침 수행하면 딥링크 버전 실행됨

- https://pay-200.vercel.app/

# Tech Stack
#### Back-end
<img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">

### ci/cd
<img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">

### Data
<img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
<img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">

### Infrastructure
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/amazonapigateway-FF4F8B?style=for-the-badge&logo=amazonapigateway&logoColor=white">
<img src="https://img.shields.io/badge/amazonroute53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">
<img src="https://img.shields.io/badge/awssecretsmanager-DD344C?style=for-the-badge&logo=awssecretsmanager&logoColor=white">
<img src="https://img.shields.io/badge/amazonwebservices-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white">

# Tech Stack Details
- Language: Kotlin 2.1.0
- JDK 21
- Spring Boot 3.4.1
- Persistence: Spring Data JPA / QueryDSL
- DB: PostgreSQL 17.2
- TEST: JUnit5

# Functional Requirements
- 프로젝트 달성 목표
    - 고객이 안전하고 빠르게 결제를 처리할 수 있는 기능을 구현합니다.
    - 트랜잭션의 신뢰성과 데이터의 일관성을 유지하면서도 응답 속도를 최적화할 수 있도록 설계합니다.


- BE 역량 목표
    - 멱등성
    - 가용성
    - 동시성


- 기능적 요구사항
    - 결제 처리 시스템
      - 다양한 결제 수단을 지원하는 결제 처리 기능 구현
    - 결제 트랜잭션 관리
      - 거래 승인, 취소, 환불 등의 거래 상태를 실시간으로 관리하고 추적
    - 결제 SDK 제공 
      - 외부 서비스에서 이 PG 서비스를 사용할 수 있는 결제 SDK를 제공 
      - 실시간 처리와 API 엔드포인트가 필요
      - 웹사이트에서 해당 SDK를 로드하고 함수 등으로 호출하여 사용
    - 사용자 모바일 앱
      - QR 코드를 읽어서 결제하는 모바일 애플리케이션을 제공
    - 백오피스
      - 사업자가 로그인 하여 결제 내역을 확인
      - 결제 내역을 이름, 날짜, 종류(결제, 취소, 환불)로 필터링이 가능
      - 복수의 사업자가 사용할 수 있다고 가정하며, 데스크톱 환경만 고려


- 기술적 도전 과제
    - 필수
        - 결제 실패 및 오류 처리
          - 결제 과정에서 발생할 수 있는 다양한 실패 시나리오(ex: 네트워크 오류, 결제 거절 등)을 처리 
          - 재시도 로직을 구현하여 사용자 경험을 개선
        - 백오피스 
          - 발급된 api키는 다른 사람과 중복 불가능 
          - 다음번 발급될 api키가 예측 가능해서도 안됨
    - 권장
        - 통합 및 확장 가능성
          - 새로운 결제 수단이나 기능의 빠른 추가를 위한 모듈화된 시스템을 설계
        - 성능 최적화 및 확장성
          - 높은 트랜잭션 처리량을 위해 결제 처리 시스템의 성능을 최적화 
          - 수요 증가에 따라 시스템이 확장될 수 있도록 설계. 로드 밸런싱, 캐싱 그리고 분산 처리 시스템을 고려	

# Infrastructure


## 특징
### 네트워크 아키텍처
- 이중화 안정성을 위해 Zone을 2개로 분리
- Public Subnet
    - API Gateway, ALB(Application Load Balancer), NAT Gateway를 배치하여 외부 요청을 처리
- Private Subnet
    - DB 서버 구축

### 서버 인프라 구성
- Docker 컨테이너 기반으로 애플리케이션을 실행
- 개발 환경과 운영 환경의 일관성을 유지
- 배포의 유연성을 확보
- EC2 인스턴스에서 직접 Docker를 실행하여 컨테이너를 배포하는 방식을 선택

### 추후 개선 방향
- AWS ECS나 Kubernetes(EKS) 같은 관리형 서비스를 활용

# DB

# preview

### app

### BackOffice
