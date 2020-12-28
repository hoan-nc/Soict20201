$(document).ready(function () {
    $(document).ready(getStatisticDermatologyByType("BT", 'bình thường'))

    $("#dermatology").change(function (event) {
        const typeSelected = event.target.value;
        let typeShow = 'không bình thường';
        if(typeSelected === 'BT'){
            typeShow = 'bình thường';
        }
        getStatisticDermatologyByType(typeSelected, typeShow);
    })

    function getStatisticDermatologyByType(type, typeShow) {
        $.ajax({
            url: `/admin/statistic-dermatology/${type}`,
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
        Highcharts.chart('container-bar-dermatology', {
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
                    text: 'Số lượng hồ sơ da liễu ' + typeShow
                }
            }],
            series: [{
                name: 'Số lượng',
                data: viewCounts
            }]
        });
    }

    function drawTable(nameArr, valueArr) {
        $('#tableStatisticDermatology').DataTable({
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
            searching: false
        });

    }
});
