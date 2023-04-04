<template>
	<view>
		<view class="all_view">
			<view style="font-weight:bold;font-size:150%;padding:4%;">
				资产
			</view>
			<view class="view_2">
				<uni-row>
					<uni-col :span="8">
						净资产
						<view style="font-weight:600;font-size: 180%;">{{netAssets}}</view>
					</uni-col>
					<uni-col :span="8">
						总资产
						<view style="font-weight:600;font-size: 180%;">{{assets}}</view>
					</uni-col>
					<uni-col :span="8">
						负债
						<view style="font-weight:600;font-size: 180%;">{{debts}}</view>
					</uni-col>
				</uni-row>
			</view>
			<view class="charts-box">
				<qiun-data-charts type="bar" :opts="opts" :chartData="chartData" />
			</view>
		</view>
		<view class="p_view">
			<view class="title">
				资产账户
			</view>
			<view class="p_list" v-for="(item,index) in propertyList" :key="index">
				<!-- <navigator url="/subpkg/prodetail/prodetail?accountDetailId= + item.accountDetailId"> -->
				<view class="p_card" @click="GoToprodetail(index)">
					<img  class="c_img" src="../../static/icon_p6208913rbe/weixin.png" mode="widthFix">
					<view class="c_name">
						{{item.accountDetailName}}
					</view>
					<view class="c_amount">
						￥{{item.balance}}
					</view>
				</view>
				<!-- </navigator> -->
			</view>
			<navigator url="/subpkg/addpro/addpro">
				<view class="navi">
					<uni-icons type="plusempty"></uni-icons>添加账户
				</view>
			</navigator>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				uid: 1,
				netAssets: '0',
				assets: '0',
				debts: '0',
				propertyList: [],
				accountList: [],
				imgList: [],
				chartData: {},
				opts: {
					color: ["#15D8AC"],
					enableScroll: false,

					legend: {
						show: false,
					},
					padding: [20, 50, 0, 0],

					xAxis: {
						boundaryGap: "justify",
						disableGrid: false,
						min: 0,
						axisLine: false,
						max: 10,
						disableGrid: true,
						disabled: true,
					},
					yAxis: {
						padding: 1,
					},
					extra: {
						bar: {
							type: "group",
							width: 25,
							meterBorde: 1,
							meterFillColor: "#FFFFFF",
							activeBgColor: "#000000",
							activeBgOpacity: 0.08,
							linearType: "custom",
							barBorderCircle: true,
							seriesGap: 1,
							categoryGap: 1
						}
					}
				},
			}
		},
		onShow() {
			this.getAssetsAndDebt();
			this.getServerData();
			// this.getAccountList();
			this.getPropertList();
		},
		methods: {
			GoToprodetail(index){
				// let item = this.propertyList[index].accountDetailId;
				// console.log(this.propertyList[index])
				let item = encodeURIComponent(JSON.stringify(this.propertyList[index]))
				uni.navigateTo({
					url: '/subpkg/prodetail/prodetail?item=' + item
				})
			},
			async getAssetsAndDebt() {
				const {
					data: res
				} = await uni.$http.get('/account/getAssetsAndDebt?uid=' + this.uid)
				if (res.msg !== "成功") return uni.$showMsg()
				this.assets = res.data.data.assets
				this.debts = res.data.data.debts
				this.netAssets = res.data.data.netAssets
				// console.log(this.debts)
			},
			getServerData() {
				//模拟从服务器获取数据时的延时
				setTimeout(() => {
					let a = this.assets;
					let b = this.debts;
					let res = {
						categories: ["总资产", "负债"],
						series: [{
							name: "金额",
							data: [a, b]
						}, ]
					};
					this.chartData = JSON.parse(JSON.stringify(res));
				}, 500);
			},
			// async getAccountList() {
			// 	const {
			// 		data: res
			// 	} = await uni.$http.get('/account/getAccountList?uid=' + this.uid)
			// 	if (res.status == 400) return uni.$showMsg()
			// 	this.accountList = res.data.data
			// 	// console.log("123",this.accountList[0].accountId)
			// },
			async getPropertList() {
				// let list = [];
				// this.accountList.forEach((item,index)=>{
				// 	list.push(accountId:accountList.accountId)
				// })
				const {
					data: res
				} = await uni.$http.get('/account/getBalanceList?uid=' + this.uid)
				if (res.msg !== "成功") return uni.$showMsg()
				this.propertyList = res.data.data
				// console.log(this.propertyList[0])
				// this.propertyList.forEach()
			},
			
		}
	}
</script>

<style>
	.all_view {
		margin: 2%;
		background-color: white;
		height: 50%;
	}

	.view_2 {
		margin-left: 5%;
	}

	.charts-box {
		margin-left: 2%;
		width: 100%;
		height: 150px;
	}

	.p_view {
		margin: 5% 2% 2% 2%;
	}
	.p_list{
		margin: 5% 5%
	}
	.p_view .title {
		font-size: 120%;
	}
	
	.p_view .p_card{
		background-color: #56DFC0;
		box-shadow: 0 3px 8px rgba(0,37,204, 0.2);
		display: flex;
		flex-flow: row;
		justify-content: start;
		align-items: center;
		/* margin: 5% 0%; */
		height: 180upx;
		opacity: 0.9;
		box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.1);;
	}
	
	.p_view .p_card .c_img{
		background-color: white;
		width: 20%;
		margin: 3%;
		border-radius: 50%;
		height: 70%;
	}
	
	.p_view .p_card .c_name{
		width: 30%;
		font-size: 120%;
	}
	
	.p_view .p_card .c_amount{
		width: 30%;
		/* margin-top: 8%; */
		margin-left: 10%;
		font-size: 120%;
	}
	.navi{
		background-color: #56DFC0;
		box-shadow: 0 3px 8px rgba(0,37,204, 0.2);
		display: flex;
		justify-content: center;
		align-items: center;
		height: 100upx;
		margin: 0% 5%;
		opacity: 0.9;
	}
</style>