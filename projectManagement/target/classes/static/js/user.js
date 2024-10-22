// "Find By ID Functionality"
async function findById(url,id){
    console.log("find by id")

    const response = await fetch(url + "/" + id,
        {
            method: "GET"
        }
    );
    if (! response.ok){
        showErrorPopup(url,response.status , (await response.text()).toString());
    }else{
        let user = JSON.parse(await response.text());

        let editModal = document.getElementById("edit-modal");
        editModal.style.display="flex";

        let idEdit= document.querySelector("#edit-modal #id");
        idEdit.value = user.id;

        let usernameEdit = document.querySelector("#edit-form div :nth-child(2)");
        usernameEdit.value = user.username;

        let passwordEdit = document.querySelector("#edit-form div :nth-child(5)");
        passwordEdit.value = user.password;

        let personEdit = document.querySelector("#edit-form div :nth-child(8)");
        personEdit.value = user.person;

        let roleSetEdit = document.querySelector("#edit-form div :nth-child(11)");
        roleSetEdit.value =user.role;

    }

}