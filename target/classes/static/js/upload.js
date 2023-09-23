// (1) 스토리 이미지 업로드를 위한 사진 선택 로직
function imageChoose(obj) {
	let f = obj.files[0];

	if (!f.type.match("image.*")) {
		alert("이미지를 등록해야 합니다.");
		return;
	}

	let reader = new FileReader();
	reader.onload = (e) => {
		$("#imageUploadPreview").attr("src", e.target.result);
	}
	reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
}

// 서버연결 - Firebase

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyBOj4CG2BNePwGn7iMEiFdabmaTdWqigzE",
  authDomain: "photogram-c75b8.firebaseapp.com",
  projectId: "photogram-c75b8",
  storageBucket: "photogram-c75b8.appspot.com",
  messagingSenderId: "392977003835",
  appId: "1:392977003835:web:30b1d7712e81151e71731e",
  measurementId: "G-N0LJ5PPQE4"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
// profile.js에서 필요한 기능 및 코드 추가