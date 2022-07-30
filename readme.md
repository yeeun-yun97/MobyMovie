# MobyMovie 무비무비
## 개요
- 네이버 영화 API를 사용하여 영화를 검색할 수 있는 애플리케이션
- 검색 결과를 클릭하면 영화관련 URL을 브라우저로 열어준다.
- 최근 검색어를 10건 저장하여 보여준다.

## 버전 1.1
log
- recyclerView에 shimmer 적용
- imeAction 설정하여 키보드 엔터로 검색시작
- Internet 연결 확인하도록 변경
- add tests (source, repository, viewModel)

## 기간
-2022.07.29 ~ 2022.07.31

## 스택
<img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=Android&logoColor=black"/> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=black"/> 

## 설명
- Kotlin + MVVM + LiveData + (Retrofit, Room)
- UI 깔끔하게 만들어보기
- 데이터가 없을 때, 빈 화면을 보여주기보다, UI로 알려주기!
- 로딩 시 shimmer로 사용자가 로딩중인지 아닌지 알기 쉽게 하기!
- 최근 검색어 중복 제거하기, 최신순으로 보여주기
- 끝까지 스크롤 했을 때 불러올 데이터가 더 있으면 불러오기 (Endless RecyclerView)
- 영화 이미지가 없을 때 기본 이미지 보여주기
- 검색어와 일치하는 제목부분 bold처리 (TextView에 Html태그 적용)
- 테스트 코드 추가하기


## skills
- Retrofit
- Room
- LiveData
- ViewModel
- dataBinding
- lifecycle
- coroutine
- AndroidJUnit4
- Glide
- Shimmer

## ui 디자인
- figma를 사용하여 먼저 ui를 그려본 후 구현하였습니다.
https://www.figma.com/file/U0pLpUljDwN4e1BOo2n1Ca/MobyMoview?node-id=0%3A1
