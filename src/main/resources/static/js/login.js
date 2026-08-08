const togglePassword = document.getElementById("togglePassword");
const passwordInput = document.getElementById("passwordInput");

togglePassword.addEventListener("click", () => {
	const type = passwordInput.type === "password" ? "text" : "password";
	passwordInput.type = type;

	togglePassword.classList.toggle("bi-eye-fill");
	togglePassword.classList.toggle("bi-eye-slash-fill");
});