// Call the dataTables jQuery plugin
$(document).ready(function () {
    $('#dataTable').DataTable();
    console.log("123");

    //EDIT EXAMINATION MODAL
    $('#content #btnEditExaminationRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        console.log(href);
        $.get(href, function (examinationEntity) {
            console.log('examinationEntity', examinationEntity)
            $('#editExaminationModal #idEdit').val(examinationEntity.id);
            $('#editExaminationModal #nameEdit').val(examinationEntity.name);
            $('#editExaminationModal #createdDateEdit').val(examinationEntity.createdDate);
        });
        $('#editExaminationModal').modal();
    });


    //EDIT DEPARTMENT EXAM MODAL
    $('#content #btnEditDepartmentExamRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        console.log(href);
        $.get(href, function (departmentExamEntity) {
            console.log('departmentExamEntity', departmentExamEntity)
            $('#editDepartmentExamModal #idEdit').val(departmentExamEntity.id);
            $('#editDepartmentExamModal #nameEdit').val(departmentExamEntity.name);
            $('#editDepartmentExamModal #phoneNumberEdit').val(departmentExamEntity.phoneNumber);
            $('#editDepartmentExamModal #addressEdit').val(departmentExamEntity.address);
        });
        $('#editDepartmentExamModal').modal();
    });


});
