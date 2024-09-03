window.onload = function() {
           document.getElementById("address_kakao").addEventListener("click", function() {
               new daum.Postcode({
                   oncomplete: function(data) {
                       document.getElementById("address_kakao").value = data.address;
                       document.querySelector("input[name=address_detail]").focus();
                   }
               }).open();
           });
       }

       function limitLength(input, maxLength) {
           if (input.value.length > maxLength) {
               input.value = input.value.slice(0, maxLength);
           }
       }

       document.querySelectorAll('.input-box input').forEach(function(input) {
           input.addEventListener('focus', function() {
               this.value = '';
           });
       });

       function validateForm() {
           let isValid = true;

           // 이메일 검증
           const emailUser = document.getElementById("emailUser").value.trim();
           const emailDomainSelect = document.getElementById("emailDomain").value;
           const customEmailDomain = document.getElementById("customEmailDomain").value.trim();
           const emailError = document.getElementById("emailError");

           if ((emailUser && (emailDomainSelect === "" || (emailDomainSelect === "custom" && customEmailDomain === ""))) ||
               (!emailUser && (emailDomainSelect !== "" && emailDomainSelect !== "custom"))) {
               emailError.style.display = "block";
               emailError.textContent = "이메일을 모두 입력해 주세요.";
               isValid = false;
           } else {
               emailError.style.display = "none";
           }

           // 전화번호 검증
           const countryNum = document.getElementById("countryNum").value;
           const userPart1 = document.getElementById("userPart1").value.trim();
           const userPart2 = document.getElementById("userPart2").value.trim();
           const phoneError = document.getElementById("phoneError");

           if ((countryNum && (!userPart1 || !userPart2)) || ((!countryNum || countryNum === "") && (userPart1 || userPart2))) {
               phoneError.style.display = "block";
               phoneError.textContent = "전화번호를 모두 입력해 주세요.";
               isValid = false;
           } else {
               phoneError.style.display = "none";
           }

           return isValid; // 폼 제출을 허용하거나 중지
       }

       function toggleCustomDomain() {
           var emailDomainSelect = document.getElementById("emailDomain");
           var customDomainInput = document.getElementById("customEmailDomain");

           if (emailDomainSelect.value === "custom") {
               customDomainInput.style.display = "inline-block"; 
               customDomainInput.name = "emailDomain"; 
               emailDomainSelect.name = "";  
           } else {
               customDomainInput.style.display = "none";  
               emailDomainSelect.name = "emailDomain";  
               customDomainInput.name = "customEmailDomain";  
           }
       }

       function prepareSubmit() {
           // 이메일 조합
           const emailUser = document.getElementById("emailUser").value.trim();
           const emailDomainSelect = document.getElementById("emailDomain").value;
           let emailDomain = emailDomainSelect === "custom" ? document.getElementById("customEmailDomain").value.trim() : emailDomainSelect;
           const fullEmail = emailUser && emailDomain ? emailUser + "@" + emailDomain : "";
           document.getElementById("fullEmail").value = fullEmail;

           // 전화번호 조합
           const countryNum = document.getElementById("countryNum").value;
           const userPart1 = document.getElementById("userPart1").value.trim();
           const userPart2 = document.getElementById("userPart2").value.trim();
           const fullPhone = countryNum && userPart1 && userPart2 ? `${countryNum}-${userPart1}-${userPart2}` : "";
           document.getElementById("fullPhone").value = fullPhone;

           // 폼 유효성 검사
           return validateForm();
       }

       function previewImage(event) {
           var input = event.target;

           if (input.files && input.files[0]) {
               var reader = new FileReader();

               reader.onload = function(e) {
                   var preview = document.getElementById('preview');
                   var imgIn = document.getElementById('img-in');
                   preview.src = e.target.result; 
                   imgIn.style.display = 'none';
                   preview.style.display = 'block';
               }

               reader.readAsDataURL(input.files[0]); 
           }
       }