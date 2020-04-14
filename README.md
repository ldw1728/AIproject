# AIproject

## 소개
내용 : 구현된 AI알고리즘을 테스트하기 위한 어플리케이션이며 패션관련 이미지들을 학습시켜 시용자의 사진을 평가해주는 기능을 가지고 있으며, 각 사용자들 서로의 사진을 확인 및 평점을 매기는 기능을 구현.

## 기술
client : android studio(java)
serveer : AWS ec2, python flask, Firebase RealTime DataBase, Firebase Authentication

## 기능 
* Firebase Authentication을 이용한 간단한 로그인 가입 기능.
* 앨범 혹은 직접 바로 촬영한 사용자의 사진을 평가
* 각각 의 사용자들은 자신의 사진을 업로드 할 수 있으며 평점을 매길 수 있다.

## 구동과정 
*  Firebase Authentication 이용하여 로그인
* 사용자의 사진은 aws ec2서버로 전송, 서버는 flask를 이용하여 http request를 받음.
* 받은 이미지는 ai알고리즘을 거쳐 나온 평가값을 다시 앱으로 response 해준다.
* 앱에서 받은 평가값을 클라이언트에게 보여준다.  
* firebase DataBase를 이용하여 사용자들이 업로드한 게시물들을 관리.

