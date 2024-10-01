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
        let person = JSON.task(await response.text());

        let editModal = document.getElementById("edit-modal");
        editModal.style.display="flex";

        let idEdit= document.querySelector("#edit-modal #id");
        idEdit.value = task.id;

        let titleEdit = document.querySelector("#edit-form div :nth-child(2)");
        titleEdit.value = task.title;

        let descriptionEdit = document.querySelector("#edit-form div :nth-child(5)");
        descriptionEdit.value = task.description;

        let assignedToEdit = document.querySelector("#edit-form div :nth-child(8)");
        assignedToEdit.value = task.assignedTo.username;

        let createDateEdit = document.querySelector("#edit-form div :nth-child(11)");
        createDateEdit.value =task.createDate;

        let dueDateEdit = document.querySelector("#edit-form div :nth-child(14)");
        dueDateEdit.value = task.dueDate;

        let priorityEdit = document.querySelector("#edit-form div :nth-child(17)");
        priorityEdit.value = task.priority;

        let statusEdit = document.querySelector("#edit-form div :nth-child(20)");
        statusEdit.value = task.status;

        let subTasksEdit = document.querySelector("#edit-form div :nth-child(23)");
        subTasksEdit.value = task.subTasks;

    }

}