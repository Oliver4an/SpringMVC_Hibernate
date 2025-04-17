  // modal.js
const Modal = {
    config: {
      title: "Default Title",
      message: "Default message",
      placeholder: "Enter something...",
      onSubmit: () => {},
    },
  
    open({ title, message, placeholder, onSubmit }) {
      this.config = { title, message, placeholder, onSubmit };
  
      document.getElementById("modalTitle").textContent = title;
      document.getElementById("modalMessage").textContent = message;
      document.getElementById("modalInput").placeholder = placeholder;
      document.getElementById("modalInput").value = "";
  
      document.getElementById("modalSubmitBtn").onclick = () => {
        const input = document.getElementById("modalInput").value;
        onSubmit(input);
      };
  
      document.getElementById("customModal").classList.remove("hidden");
    },
  
    close() {
      document.getElementById("customModal").classList.add("hidden");
    }
  };
  