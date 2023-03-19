<template>
	<view>
		<view class="inv-h-w">
			<block v-for="(item,index) in items" :key="index">
				<view :class="['inv-h',Inv== index?'inv-h-se':'']" @click="Inv=index">{{item}}</view>
			</block>
			<!--<view :class="['inv-h',Inv==1?'inv-h-se':'']" @click="Inv=1">我是选项卡二</view>-->
		</view>
		<view class="" v-show="Inv == 0">
			<view id="echarts">
				<view class="lineChart">
					<testEchart ref="lineChartRef"></testEchart>
				</view>
			</view>
		</view>
		<view class="" v-show="Inv == 1">

		</view>
			<bxTabber></bxTabber>
		</view>
</template>

<script>
	import bxTabber from '@/components/bx-tabber.vue';
	import testEchart from '@/components/charts_test.vue';
	export default {
		components: {
			bxTabber,
			testEchart
		},
		data() {
			return {
				option: {},
				Inv: 0,
				items: ['支出', '收入']
			}
		},
		onReady() {
			this.setEchart()
		},
		methods: {
			changeTab(Inv) {
				that.navIdx = Inv;
			},
			setEchart() {
				let series = {
					data: [
						['2022-12-1', 20],
						['2022-12-2', 22],
						['2022-12-3', 23],
						['2022-12-4', 18],
						['2022-12-5', 27],
						['2022-12-6', 30],
						['2022-12-7', 29],
						['2022-12-8', 32],
						['2022-12-9', 31],
						['2022-12-10', 31],
						['2022-12-11', 29],
						['2022-12-12', 26],
					],
					type: 'line',
				};
				let title = {
					text: '测试',
					left: 'center',
					top: '3%',
					textStyle: {
						fontSize: 18,
						color: 'rgb(0,0,0)',
						fontWeight: 'normal',
					},
				};
				if (this.$refs.lineChartRef) {
					let options = this.$refs.lineChartRef.options;
					options.series = series;
					options.title = title;
					this.$refs.lineChartRef.init(options);
				}
			},

		}
	}
</script>

<style>
	.inv-h-w {
		background-color: #FFFFFF;
		height: 100upx;
		display: flex;
	}

	.inv-h {
		font-size: 32upx;
		flex: 1;
		text-align: center;
		color: #666666;
		height: 100upx;
		line-height: 100upx;
		position: relative;
	}

	.inv-h-se {
		font-size: 36rpx;
		font-family: PingFang SC;
		font-weight: bold;
		color: #55aaff;
	}

	.inv-h-se:after {
		content: '';
		position: absolute;
		bottom: -2rpx;
		top: auto;
		left: 42%;
		height: 6rpx;
		width: 44rpx;
		background-color: #55aaff;
	}

	page {
		background-color: #F2F2F2;
	}

	#echarts {
		width: 100%;
		height: 100vh;
		background: #aaa;
		padding: 5px;
	}

	.lineChart {
		width: 100%;
		height: 250px;
	}
</style>
