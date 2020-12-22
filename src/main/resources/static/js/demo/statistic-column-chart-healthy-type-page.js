$(document).ready(function () {
    $(document).ready(getStatisticDataByYear(2020))

    $("#healthType").change(function (event) {
        const yearSelected = event.target.value;
        getStatisticDataByYear(yearSelected);
    })

    function getStatisticDataByYear(year) {
        $.ajax({
            url: `/admin/statistic-healthy-type/${year}`,
            success: function (result) {
                let nameArr = [];
                let valueArr = [];
                Object.keys(result).forEach(
                    function (key) {
                        nameArr.push(key);
                        valueArr.push(result[key]);
                    });
                drawChart(nameArr, valueArr);
                drawTable(nameArr, valueArr);

            }
        });
    }

    function drawChart(year, viewCounts) {
        Highcharts.chart('container-bar-healthy-type', {
            chart: {
                type: 'column',
                styledMode: true
            },
            title: {
                text: ''
            },
            xAxis: [{
                title: {
                    text: 'Loại sức khỏe'
                },
                categories: year
            }],
            yAxis: [{
                className: 'highcharts-color-0',
                title: {
                    text: 'Số lượng loại sức khỏe'
                }
            }],
            series: [{
                name: 'Số lượng',
                data: viewCounts
            }]
        });
    }

    function drawTable(nameArr, valueArr) {
        $('#tableStatisticHealthyType').DataTable({
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
