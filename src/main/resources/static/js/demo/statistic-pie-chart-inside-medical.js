$(document).ready(function () {
    $(document).ready(
        function () {
            $.ajax({
                url: "/admin/statistic-inside-percent-now",
                success: function (result) {
                    let nameArr = [];
                    let valueArr = [];
                    console.log(result);
                    Object.keys(result).forEach(
                        function (key) {
                            nameArr.push(key);
                            valueArr.push(result[key]);
                        });
                    drawChart(nameArr, valueArr);
                }
            });
        });

    function drawChart(nameArr, valueArr) {
        Highcharts.chart('chart-pie-inside-medical', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Thống kê tỉ lệ mắc bệnh nội khoa năm 2020'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            accessibility: {
                point: {
                    valueSuffix: '%'
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    }
                }
            },
            series: [{
                name: 'Tỉ lệ',
                colorByPoint: true,
                data: valueArr.map((el, index) => ({name: nameArr[index], y: el}))
            }]
        });
    }
});
