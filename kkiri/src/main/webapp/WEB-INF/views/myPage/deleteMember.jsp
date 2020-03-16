<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.3.5/js/swiper.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"
    integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
    integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
    crossorigin="anonymous"></script>
  <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap&subset=korean"
    rel="stylesheet">
  <link rel="stylesheet" href="../index 페이지 - 진웅/css/common.css">
  <link rel="stylesheet" href="../index 페이지 - 진웅/css/header.css">
  <link rel="stylesheet" href="../index 페이지 - 진웅/css/footer.css">
  <script src='../index 페이지 - 진웅/js/index.js'></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
  <title>KKIRI(끼리)</title>
  <style>

    .wty1 {
     margin: 0px 0;
      border: 2px solid grey;
      padding: 1% 2%;
      height: 100%;
      }
    .wty2{
      background-color: skyblue;
      color: black;
      margin-top: 20px;
    }
    .wty3{
      border: 2px solid grey;
      margin-top: 20px;
    }
    .wty4{
      text-align: center;
      width: 20%;
    }
    .wty5{
      border-top: 2px solid gray;
    }
    .passborder{
      border: 1px solid grey;
    }

  </style>
</head>

<body>
  <div id="wrapper">
    <jsp:include page="../common/header.jsp"/>
    <div class="container" style="margin: 30px auto;  padding: 10px;">
      <!-- content 시작 -->
      <div class="row">
        <div class="col-3"></div>
        <div class="col-9" style="padding: 0;">
          <div id="my-profile" class="wty1 btn" style="float: left;">내 정보</div>       &nbsp;
          <div id="change-password" class="wty1 btn" style="float: left;">비밀번호 변경</div>&nbsp;
          <div id="account" class="wty1 btn" style="float: left;">환급 계좌</div>&nbsp;
          <div id="delete-member" class="wty1 btn" style="float: left;">회원탈퇴</div>&nbsp;
        </div>
      </div>
      <div class="row">
        <div class="col-3 " style="margin: 20px 0px; text-align: right;" >
          <div class="btn">프로필</div><br>
          <div class="btn">이벤트</div><br>
          <div class="btn">티켓</div><br>
          <div class="btn">로그아웃</div><br>
        </div>




        <div class="col-9" style="padding: 0;">
          <div id="display-password" class="wty3" >
            <h4>회원 탈퇴</h4>
            <hr>
            <textarea class="passborder" name="" id="" cols="30" rows="25" readonly style="height: 250px; resize:none; margin-left:13% ; width:75%;">회원 탈퇴 약관
              1조.
              회원정보 보호 약관
              얼음과 같으며 지혜는 날카로우나 갑 속에 든 칼이다 청춘의 끓는 피가 아니더면 인간이 얼마나 쓸쓸하랴? 얼음에 싸인 만물은 얼음이 있을 뿐이다 그들에게 생명을 불어 넣는 것은 따뜻한 봄바람이다 풀밭에 속잎나고 가지에 싹이</textarea>
             
             
              <form action="" method="POST"  onsubmit="return validate();">
             
                <div class="row" style="margin-top:20px" id="agree">
                  <div class="col-4">
                    위의 약관에 대해서 동의 하십니까?
                  </div>
                  <input class="col-1 passborder checker" type="radio" name="agree" style="margin-top: 7px;" id="agreement"> <label for="agreement">동의</label>
                  <input class="col-1 passborder checker" type="radio" name="agree" style="margin-top: 7px;" id="disagree"> <label for="disagree">거절</label>
                </div>
                <br>
                <div class="row" style="margin-top:20px" id="passcheck">
                  <div class="col-4">
                    비밀번호 확인
                  </div>
                  <input class="col-4 passborder checkpath" type="password" placeholder="내용을 입력해주세요" >
                </div>
                <br>
                <div class="row" style="margin-top:20px" id="deletemember">
                  <div class="col-4">
                    모든 약관에 동의 하시며, <br>회원 탈퇴를 원하실 경우 <br>'회원탈퇴' 라고 적어주시기 바랍니다.
                  </div>
                  <input class="col-4 passborder checkpath" type="text" placeholder="회원탈퇴" id="delete-flag" >
                </div>
                <br>
                <div class="row" >
                  <div class="btn wty2" style=" margin-left: 40%; margin-bottom : 10px; width: 12%;" >
                    이전
                  </div>
                  <div class="btn wty2" style=" margin-left: 2%; margin-bottom : 10px; width: 12%;" >
                    회원탈퇴
                  </div>
                </div>
              </form>  
            </div>
        </div>
      </div>
    </div>
    
    <script>

      var signUpCheck = {
      "agree" : false,
      "passcheck" :false,
      "deletemember" : false
      }

      var agree = $("#agree");
      var passcheck = $("#passcheck");
      var deletemember = $("#deletemember");
      var flag = true;
      
    $(function(){
//  동의 거절 체크시 다른 작업 불가 기능
      $(".checker").change(function(){
        if($("#disagree").is(":checked")){
            $(".checkpath").prop('readonly',true);
            flag = false;
            signUpCheck.agree = false;
        }else{
            $(".checkpath").prop('readonly',false);
            flag = true;
            signUpCheck.agree = true;
        }
      })
      $(".checkpath").click(function(){
        if(!flag){
         alert("약관에 동의해주세요.");
         return false;
        }
      })

      // 비밀번호 체크 부분

      $("")
     
     // 회원탈퇴 입력 부분
     $("#delete-flag").change(function(){
     
     if($("#delete-flag").val()=='회원탈퇴'){
       signUpCheck.deletemember = true;
       alert('성공')

     }else{
       signUpCheck.deletemember = false;

     }
    })
  })
      function validate(){
        for(var flag in signUpCheck){
          if(!signUpCheck[flag]){
            alert("미 작성된 부분이 있습니다.")
            var id = "#"+flag;
					  $(id).focus();
            return false;
          }
        }
      }
    </script>


    
    
  


  <!-- content 끝 -->
  
  <div id="footer">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="row">
            <div class="col-md-4 footer-logo-wrap">
              <img class="footer-logo" src="../index 페이지 - 진웅/img/logo2-white.png" alt="푸터로고">
            </div>
            <div class="col-md-8">
              <p class="copyright">&copy; 2020 KKIRI COMPANY. ALL RIGHTS RESERVED.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div id="button-top">
    <button type="button" class="top-btn">TOP</button>
  </div>
</div>
  <script>
    function scrollFunction() {
      if ($(window).scrollTop() >= 200) {
        $('#button-top').show(0);
      } else {
        $('#button-top').hide(0);
      }
    }
    $(function () {
      scrollFunction();
      $(window).scroll(function () {
        scrollFunction();
      });
      $('#button-top').on({
        click: function () {
          $('html,body').stop().animate({ scrollTop: 0 }, 600);
        }
      });
    });
  </script>
</body>

</html>