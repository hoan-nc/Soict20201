$(document).ready(function () {
    $(document).ready(getStatisticInsideMedicalType("BT", 'bình thường'))

    $("#insideMedical").change(function (event) {
        const typeSelected = event.target.value;
        let typeShow = 'không bình thường';
        if(typeSelected === 'BT'){
            typeShow = 'bình thường';
        }
        getStatisticInsideMedicalType(typeSelected, typeShow);
    })

    function getStatisticInsideMedicalType(type, typeShow) {
        $.ajax({
            url: `/admin/statistic-inside-medical/${type}`,
            success: function (result) {
                let nameArr = [];
                let valueArr = [];
                Object.keys(result).forEach(
                    function (key) {
                        nameArr.push(key);
                        valueArr.push(result[key]);
                    });
                drawChart(typeShow, nameArr, valueArr);
                drawTable(nameArr, valueArr);

            }
        });
    }

    function drawChart(typeShow, year, viewCounts) {
        Highcharts.chart('container-bar-inside-medical', {
            chart: {
                type: 'column',
                styledMode: true
            },
            title: {
                text: ''
            },
            xAxis: [{
                title: {
                    text: 'Năm'
                },
                categories: year
            }],
            yAxis: [{
                className: 'highcharts-color-0',
                title: {
                    text: 'Số lượng hồ sơ bệnh nội khoa ' + typeShow
                }
            }],
            series: [{
                name: 'Số lượng',
                data: viewCounts
            }]
        });
    }

    function drawTable(nameArr, valueArr) {
        $('#tableStatisticNerve').DataTable({
            data: valueArr.map((item, index) => ({
                idx: index + 1,
                type: nameArr[index],
                value: valueArr[index],
            })),
            columns: [
                {data: 'idx'},
                {data: 'type'},
                {data: 'value'},
            ],
            destroy: true,
            searching: false,
            retrieve: true,
        });

    }
});
