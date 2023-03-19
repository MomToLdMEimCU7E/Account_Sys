<template>
	<view class="chart">
		<l-echart ref="chart"></l-echart>
	</view>
</template>

<script>
	import moment from 'moment';
	import * as echarts from 'echarts';
	export default {
		data() {
			return {
				options: {
					color: ['#9dd3e8'],
					title: {
						x: "center",
						y: "bottom",
						textStyle: { // 文字样式
							color: '#aaaaaa',
							fontWeight: 700,
							fontSize: 18,
							textShadowColor: '#000000', // 文字阴影颜色
							textShadowBlur: 1, // 文字阴影宽度
						},
						subtext: '', //副标题
					},
					tooltip: {
						trigger: 'axis', // 触发类型  item/axis  
						confine: true,
					},
					legend: {
						type: 'plain',
						orient: 'horizontal',
					},
					grid: {
						left: "10%",
						right: '5%',
						top: '15%',
						bottom: '8%',
					},
					xAxis: [{
						type: 'time',
						// splitNumber:3,
						axisLine: {
							onZero: false,
							lineStyle: {
								color: '#000',
							}
						},
						splitLine: {
							show: true,
						},
						axisLabel: {
							textStyle: {
								fontSize: '10',
							},
							interval: 4,
							formatter: function(item, index) {
								let value = ''

								if (moment(item).hour() == 0) {
									value = moment(item).format('MM月DD日');
									if (index === 1) {
										value = moment(item).format('YYYY年MM月');
									}
								} else if (moment(item).minute() == 0) {
									value = moment(item).format('HH时');
								} else {
									value = moment(item).format('HH时mm分');
								}
								console.log(value)
								return value;
							}
						},
					}],
					yAxis: [{
						type: 'value',
						name: '',
						nameTextStyle: {
							fontSize: 14,
							color: 'rgb(7,141,206)',
						},
						axisLine: {
							show: true,
							lineStyle: {
								color: '#000',
							},
						},
						axisLabel: {
							show: true,
							fontSize: 12,
						},
						axisTick: {
							show: true,
						},
					}],
					series: [{
						type: 'line',
						smooth: true,
						showSymbol: false,
						markLine: {
							silent: false,
							data: [],
						},
						data: [],
					}],
					dataZoom: []
				},

			};
		},
		methods: {
			init(options) {
				this.$refs.chart.init(echarts, chart => {
					chart.setOption(options);
				});
			}
		}
	}
</script>

<style>
	.chart {
		width: 100%;
		height: 100%;
		background-color: #fff;
	}

	::v-deep .lime-echart {
		height: 100% !important;
		width: 100% !important;
	}
</style>
