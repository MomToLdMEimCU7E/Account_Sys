<template>
	<view>
		<view class="inv-h-w">
			<block v-for="(item,index) in payItems" :key="index">
				<view :class="['inv-h',Inv== index?'inv-h-se':'']" @click="Inv=index">{{item}}</view>
			</block>

		</view>
		<view class="" v-show="Inv == 0">
			<view class="uni-padding-wrap uni-common-mt">
				<uni-segmented-control :current="current" :values="items" styleType="button" active-color="#15D8AC"
					@clickItem="onClickItem" />
			</view>
			<view class="control">
				<view v-if="current === 0">
					<view class="charts-box">
						<qiun-data-charts type="column" :opts="opts" :chartData="chartData" />
						<qiun-data-charts 
						      type="ring"
						      :opts="opts2"
						      :chartData="chartData2"
						    />
					</view>
				</view>
				<view v-if="current === 1">
					<text class="content-text">选项卡2的内容</text>
				</view>
				<view v-if="current === 2">
					<text class="content-text">选项卡3的内容</text>
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
	export default {
		components: {
			bxTabber,
		},
		data() {
			return {
				option: {},
				Inv: 0,
				current: 0,
				payItems: ['支出', '收入'],
				items: ['周', '月', '年'],
				chartData: {},
				chartData2: {},
				opts: {
					color: ["#15D8AC"],
					padding: [15, 15, 0, 5],
					enableScroll: false,
					legend: {
						// backgroundColor: '#15D8AC',
					},
					xAxis: {
						disableGrid: true
					},
					yAxis: {
						gridType: 'dash',
						data: [{
							min: 0
						}]
					},
					extra: {
						column: {
							type: "group",
							width: 30,
							activeBgColor: "#000000",
							activeBgOpacity: 0.08
						}
					}
				},
				opts2: {
				        rotate: false,
				        rotateLock: false,
				        color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
				        padding: [5,5,5,5],
				        dataLabel: true,
				        enableScroll: false,
				        legend: {
				          show: true,
				          position: "right",
				          lineHeight: 25
				        },
				        title: {
				          name: "当前收支情况",
				          fontSize: 15,
				          color: "#666666"
				        },
				        extra: {
				          ring: {
				            ringWidth: 60,
				            activeOpacity: 0.5,
				            activeRadius: 10,
				            offsetAngle: 0,
				            labelWidth: 15,
				            border: true,
				            borderWidth: 3,
				            borderColor: "#FFFFFF",
				            linearType: "custom"
				          }
				        }
				      }
			}
		},
		onReady() {
			this.getServerData();
			this.getServerData2();
		},
		methods: {
			changeTab(Inv) {
				that.navIdx = Inv;
			},
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
			},
			getServerData() {
				//模拟从服务器获取数据时的延时
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ["周一", "周二", "周三", "周四", "周五", "周六"],
						series: [{
								name: "支出",
								data: [35, 36, 31, 33, 13, 34]
							},
							
						]
					};
					this.chartData = JSON.parse(JSON.stringify(res));
				}, 500);
			},
			getServerData2() {
			      //模拟从服务器获取数据时的延时
			      setTimeout(() => {
			        //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
			        let res = {
			            series: [
			              {
			                data: [{"name":"一班","value":50},{"name":"二班","value":30},{"name":"三班","value":20},{"name":"四班","value":18},{"name":"五班","value":8}]
			              }
			            ]
			          };
			        this.chartData2 = JSON.parse(JSON.stringify(res));
			      }, 500);
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

	.charts-box {
		width: 100%;
		height: 300px;
		margin-top: 50%;
		background-color: #FFFFFF;
	}

	.control {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		justify-content: center;
		align-items: center;
		height: 150px;
		text-align: center;
	}

	.uni-common-mt {
		margin-top: 30px;
	}

	.uni-padding-wrap {
		// width: 750rpx;
		padding: 0px 30px;
	}
</style>