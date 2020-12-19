// Call the dataTables jQuery plugin
$(document).ready(function () {
    $('#dataTable').DataTable();

    //CONFIRM DELETE GENERAL PROFILE MODAL
    $('#content #deleteProfileButtonRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#deleteConfirmGeneralProfileModal #deleteGeneralProfileRef').attr('href', href);
        $('#deleteConfirmGeneralProfileModal').modal();
    })


    //CONFIRM DELETE EXAMINATION MODAL
    $('#content #btnDeleteProfileButtonRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#deleteConfirmPermissionModal #deletePermissionRef').attr('href', href);
        $('#deleteConfirmPermissionModal').modal();
    })


    //EDIT EXAMINATION MODAL
    $('#content #btnEditExaminationRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (examinationEntity) {
            console.log('examinationEntity', examinationEntity)
            $('#editExaminationModal #idEdit').val(examinationEntity.id);
            $('#editExaminationModal #nameEdit').val(examinationEntity.name);
            $('#editExaminationModal #createdDateEdit').val(examinationEntity.createdDate);
        });
        $('#editExaminationModal').modal();
    });

    //CONFIRM DELETE EXAMINATION MODAL
    $('#content #btnDeleteExaminationRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#deleteConfirmExaminationModal #deleteExaminationRef').attr('href', href);
        $('#deleteConfirmExaminationModal').modal();
    })


    //EDIT DEPARTMENT EXAM MODAL
    $('#content #btnEditDepartmentExamRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (departmentExamEntity) {
            console.log('departmentExamEntity', departmentExamEntity)
            $('#editDepartmentExamModal #idEdit').val(departmentExamEntity.id);
            $('#editDepartmentExamModal #nameEdit').val(departmentExamEntity.name);
            $('#editDepartmentExamModal #phoneNumberEdit').val(departmentExamEntity.phoneNumber);
            $('#editDepartmentExamModal #addressEdit').val(departmentExamEntity.address);
        });
        $('#editDepartmentExamModal').modal();
    });

    //CONFIRM DELETE DEPARTMENT EXAM MODAL
    $('#content #btnDeleteDepartmentExamRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#deleteConfirmDepartmentExamModal #deleteDepartmentExamRef').attr('href', href);
        $('#deleteConfirmDepartmentExamModal').modal();
    })


});
