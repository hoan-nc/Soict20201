$(document).ready(function () {
    $(document).ready(
        function () {
            $.ajax({
                url: "/admin/statistic-weight",
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

    function drawChart(year, viewCounts) {
        Highcharts.chart('container-bar-weight', {
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
                    text: 'Cân nặng (kg)'
                }
            }],
            series: [{
                name: 'Trung bình',
                data: viewCounts
            }]
        });
    }
});
