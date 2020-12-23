// Call the dataTables jQuery plugin
$(document).ready(function () {
    $('#dataTable').DataTable();

    //EDIT PHYSICAL ADMIN MODAL
    $('#content #btnEditGeneralProfilesRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (physicalExamEntity) {
            console.log('physicalExamEntity', physicalExamEntity)
            $('#editPhysicalModal #idEdit').val(physicalExamEntity.id);
            $('#editPhysicalModal #userIdEdit').val(physicalExamEntity.user.id);
            $('#editPhysicalModal #fullNameEdit').val(physicalExamEntity.user.fullName);
            $('#editPhysicalModal #examinationNameEdit').val(physicalExamEntity.examination.name);
            $('#editPhysicalModal #examinationIdEdit').val(physicalExamEntity.examination.id);
            $('#editPhysicalModal #departmentExamNameEdit').val(physicalExamEntity.departmentExam.name);
            $('#editPhysicalModal #departmentExamIdEdit').val(physicalExamEntity.departmentExam.id);
            $('#editPhysicalModal #createdDateEdit').val(physicalExamEntity.createdDate);
            $('#editPhysicalModal #heightEdit').val(physicalExamEntity.height);
            $('#editPhysicalModal #weightEdit').val(physicalExamEntity.weight);
            $('#editPhysicalModal #bloodPressureEdit').val(physicalExamEntity.bloodPressure);
            $('#editPhysicalModal #eyesEdit').val(physicalExamEntity.eyes);
            $('#editPhysicalModal #bloodAnalysisEdit').val(physicalExamEntity.bloodAnalysis);
            $('#editPhysicalModal #whiteBloodNumberEdit').val(physicalExamEntity.whiteBloodNumber);
            $('#editPhysicalModal #redBloodNumberEdit').val(physicalExamEntity.redBloodNumber);
            $('#editPhysicalModal #hemoglobinEdit').val(physicalExamEntity.hemoglobin);
            $('#editPhysicalModal #plateletNumberEdit').val(physicalExamEntity.plateletNumber);
            $('#editPhysicalModal #bloodUreaEdit').val(physicalExamEntity.bloodUrea);
            $('#editPhysicalModal #bloodCreatinineEdit').val(physicalExamEntity.bloodCreatinine);
            $('#editPhysicalModal #hepatitisBEdit').val(physicalExamEntity.hepatitisB);
            $('#editPhysicalModal #insideMedicalEdit').val(physicalExamEntity.insideMedical);
            $('#editPhysicalModal #outsideMedicalEdit').val(physicalExamEntity.outsideMedical);
            $('#editPhysicalModal #earNoseThroatEdit').val(physicalExamEntity.earNoseThroat);
            $('#editPhysicalModal #dentomaxillofacialEdit').val(physicalExamEntity.dentomaxillofacial);
            $('#editPhysicalModal #dermatologyEdit').val(physicalExamEntity.dermatology);
            $('#editPhysicalModal #nerveEdit').val(physicalExamEntity.nerve);
            $('#editPhysicalModal #healthTypeEdit').val(physicalExamEntity.healthType);
            $('#editPhysicalModal #advisoryEdit').val(physicalExamEntity.advisory);
        });
        $('#editPhysicalModal').modal();
    });

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


    //DETAIL PHYSICAL ME MODAL
    $('#content #btnDetailGeneralProfileMeRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (physicalExamEntity) {
            console.log('physicalExamEntity', physicalExamEntity)
            $('#detailPhysicalMeModal #fullNameDetail').val(physicalExamEntity.user.fullName);
            $('#detailPhysicalMeModal #examinationDetail').val(physicalExamEntity.examination.name);
            $('#detailPhysicalMeModal #departmentExamDetail').val(physicalExamEntity.departmentExam.name);
            $('#detailPhysicalMeModal #createdDateDetail').val(physicalExamEntity.createdDate);
            $('#detailPhysicalMeModal #heightDetail').val(physicalExamEntity.height);
            $('#detailPhysicalMeModal #weightDetail').val(physicalExamEntity.weight);
            $('#detailPhysicalMeModal #bloodPressureDetail').val(physicalExamEntity.bloodPressure);
            $('#detailPhysicalMeModal #eyesDetail').val(physicalExamEntity.eyes);
            $('#detailPhysicalMeModal #bloodAnalysisDetail').val(physicalExamEntity.bloodAnalysis);
            $('#detailPhysicalMeModal #whiteBloodNumberDetail').val(physicalExamEntity.whiteBloodNumber);
            $('#detailPhysicalMeModal #redBloodNumberDetail').val(physicalExamEntity.redBloodNumber);
            $('#detailPhysicalMeModal #hemoglobinDetail').val(physicalExamEntity.hemoglobin);
            $('#detailPhysicalMeModal #plateletNumberDetail').val(physicalExamEntity.plateletNumber);
            $('#detailPhysicalMeModal #bloodUreaDetail').val(physicalExamEntity.bloodUrea);
            $('#detailPhysicalMeModal #bloodCreatinineDetail').val(physicalExamEntity.bloodCreatinine);
            $('#detailPhysicalMeModal #hepatitisBDetail').val(physicalExamEntity.hepatitisB);
            $('#detailPhysicalMeModal #insideMedicalDetail').val(physicalExamEntity.insideMedical);
            $('#detailPhysicalMeModal #outsideMedicalDetail').val(physicalExamEntity.outsideMedical);
            $('#detailPhysicalMeModal #earNoseThroatDetail').val(physicalExamEntity.earNoseThroat);
            $('#detailPhysicalMeModal #dentomaxillofacialDetail').val(physicalExamEntity.dentomaxillofacial);
            $('#detailPhysicalMeModal #dermatologyDetail').val(physicalExamEntity.dermatology);
            $('#detailPhysicalMeModal #nerveDetail').val(physicalExamEntity.nerve);
            $('#detailPhysicalMeModal #healthTypeDetail').val(physicalExamEntity.healthType);
            $('#detailPhysicalMeModal #advisoryDetail').val(physicalExamEntity.advisory);
        });
        $('#detailPhysicalMeModal').modal();
    });

});
