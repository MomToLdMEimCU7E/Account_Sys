<template>
	<view>
		<scroll-view scroll-y="true" :enable-flex="true" style="overflow: auto;">
			<view class="pickerView">
				<view style="margin: 0% 3%;">
					当前账本
				</view>
				<picker v-model="bookList.bookkeepingId" :range="bookList" range-key="bookkeepingName"
					:value="bookPickerIndex" @change="changeId">
					<view>{{bookList[bookPickerIndex].bookkeepingName}}</view>
				</picker>
			</view>
			<uni-calendar class="calendar" :selected="c_date.selected" :showMonth="true" @change="change" />
			<view style="display: flex;justify-content: space-around;align-content: center;">
				<view class="navi" @click="checkSchedule()">
					<uni-icons type="calendar-filled"></uni-icons>查看日程
				</view>
				<view class="navi" @click="openAddSchedule()">
					<uni-icons type="plusempty"></uni-icons>添加日程
				</view>
			</view>
			<uni-card title="基础卡片">
				<template v-slot:title>
					<view class="b_title">
						本月总预算:{{budget}}
					</view>
				</template>
				<view class="b_text">
					<view class="strip">
						<view class="blue" :style="'width:'+info.progress+'%'">
						</view>
					</view>
					<view style="margin-top: 5%;">
						剩余预算:{{left_budget}}
					</view>
					<view class="divider"></view>
				</view>
				<view slot="actions" class="b_actions">
					<view class="button" @click="changeBudgetDialog()">
						设置预算
					</view>
				</view>
			</uni-card>
			<uni-popup ref="budgetDialog" type="dialog">
				<uni-popup-dialog type="info" cancelText="取消" confirmText="确定" title="设置预算"
					@confirm="changeBudgetConfirm()">
					<uni-forms :modelValue="updateBudget">
						<uni-easyinput v-model="updateBudget.budget" placeholder="请输入预算"></uni-easyinput>
					</uni-forms>
				</uni-popup-dialog>
			</uni-popup>
			<view class="bill_title">
				<text style="font-size: 150%;font-weight: bold;">收支记录</text>
				<!-- <select-lay :value="selecValue" placeholder="筛选" name="name" :options="options" @selectitem="selectitem"
					style="width: 80px;background-color: #56DFC0;box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);font color: black;"></select-lay> -->
			</view>
			<view class="bill_list" v-for="(item,index) in payList" :key="index">
				<view class="bill_card" @click="GoToprodetail(index)">
					<img class="bill_img" :src="item.icon" mode="widthFix">
					<view class="">
						<view class="bill_name">
							{{item.fundName}}
						</view>
						<view class="">
							{{item.time}}
						</view>
					</view>
					<view class="bill_amount">
						￥{{item.amount}}
					</view>
				</view>
			</view>
		</scroll-view>
		<uni-popup ref="dateList">
			<view class="list" v-for="(item,index) in scheduleList" :key="index">
				<view class="scheduleShow">
					<view class="title">
						{{item.title}}
					</view>
					<view class="time">
						<text style="font-size: 120%;font-weight: bold;margin-right: 5%;">时间</text>
						{{dateFormat(item.date)}}
					</view>
					<view class="pos">
						<text style="font-size: 120%;font-weight: bold;margin-right: 5%;">地点</text>
						{{item.position}}
					</view>
					<view class="thing">
						<text style="font-size: 120%;font-weight: bold;margin-right: 5%;">事件</text>
						{{item.content}}
					</view>
					<!-- <view class="divider"></view> -->
				</view>
			</view>
		</uni-popup>
		<bx-tabber></bx-tabber>
	</view>
</template>

<script>
	import bxTabber from '@/components/bx-tabber.vue';
	export default {
		components: {
			bxTabber
		},
		data() {
			return {
				uid: 1,
				selecValue: '',
				budget: '',
				left_budget: 200,
				bookkeepingId: '1',
				bookList: [],
				bookPickerIndex: 0,
				scheduleList: [],
				updateBudget: {
					bookkeepingId: '',
					bookkeepingName: '',
					bookkeepingCover: '',
					budget: '',
				},
				info: {
					progress: '',
				},
				date: '',
				c_date: {
					selected: [{
						date: '2023-04-03',
						info: '-700',
					}]
				},
				payList: [{
					icon: '../static/icon_p6208913rbe/youxi.png',
					fundName: '游戏',
					amount: '-100',
					time: '2023-04-11',
					comment: 'test',
					accountDetailName: '储蓄卡'
				},
				{
					icon: '../static/icon_p6208913rbe/youxi.png',
					fundName: '游戏',
					amount: '100',
					time: '2023-04-11',
					comment: 'test',
					accountDetailName: '储蓄卡'
				}
				]
			}
		},
		onShow() {
			this.getBookList();
			this.calucateProgress();
			this.getBudget();
			// this.init();
		},
		methods: {
			GoToprodetail(index) {
				let item = encodeURIComponent(JSON.stringify(this.payList[index]))
				uni.navigateTo({
					url: '/subpkg/billdetail/billdetail?item=' + item
				})
			},
			changeId(e) {
				this.bookPickerIndex = e.detail.value;
				this.bookkeepingId = this.bookList[this.bookPickerIndex].bookkeepingId;
				this.updateBudget = this.bookList[this.bookPickerIndex];
				this.init();
			},
			init() {
				// this.getBookList();
				this.calucateProgress();
				this.getBudget();
				this.getPaymentList();
			},
			getBookList() {
				uni.$http.get('/book/getBookList?uid=' + this.uid).then(res => {
					const myrange = [];
					if (res.data.msg !== "成功")
						return uni.$showMsg()
					else {
						this.bookList = res.data.data.data;
						this.bookkeepingId = this.bookList[0].bookkeepingId;
						this.updateBudget = this.bookList[0];
					}
					
					// console.log(this.bookList[0].bookkeepingId)
				})
			},
			getPaymentList(){
				uni.$http.get('/book/getPaymentList?bookkeepingId=' + this.bookkeepingId).then(res => {
					const myrange = [];
					if (res.data.msg !== "成功")
						return uni.$showMsg()
					else {
						this.payList = res.data.data.data;
					}
					
					// console.log(this.bookList[0].bookkeepingId)
				})
			},
			async calucateProgress() {
				await this.getBudget().then((res) => {
					this.info.progress = this.left_budget / this.budget * 100;
					// console.log(this.info.progress)
				})
			},
			async getBudget() {
				const {
					data: res
				} = await uni.$http.get('/book/getBudget?bookkeepingId=' + this.bookkeepingId)
				if (res.msg !== "成功") return uni.$showMsg()
				this.budget = res.data
				// console.log(res.data)
			},
			checkSchedule() {
				uni.$http.get('/schedule/getDayScheduleList?uid=' + this.uid + '&date=' + this.date).then(res => {
					if (res.data.msg !== "成功")
						return uni.$showMsg()
					if (res.data.data.data.length === 0)
						return uni.showToast({
							title: '该日期无日程',
							duration: 1500,
							icon: 'none',
						})
					else {
						this.scheduleList = res.data.data.data;
						this.$refs.dateList.open('center');
					}
				})
			},
			openAddSchedule() {
				this.$refs.popup.open("bottom");
			},
			change(e) {
				this.date = e.fulldate;
			},
			dateFormat(time) {
				let date = new Date(time);
				let year = date.getFullYear();
				// 在日期格式中，月份是从0开始的，因此要加0，使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
				let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
				let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
				let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
				let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
				let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
				// 拼接
				// return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
				return year + "年" + month + "月" + day + "日" + hours + "时" + minutes + "分";
			},
			changeBudgetDialog() {
				this.$refs.budgetDialog.open();
				// Object.assign(this.updateBudget.budget,this.budget);
				this.updateBudget.budget = this.budget;
				console.log(this.updateBudget.budget)
			},
			changeBudgetConfirm() {
				uni.$http.post('/book/updateBook', this.updateBudget).then(res => {
					// console.log(res.data.msg);
					if (res.data.msg === "成功") {
						this.init();
						return uni.showToast({
							title: '修改成功！',
							duration: 1500,
							icon: 'none',
						});
					} else return uni.showToast({
						title: '修改失败！',
						duration: 1500,
						icon: 'none',
					})
				})

				this.$refs.budgetDialog.close()
			},
			selectitem(index, item) {
				console.log(item)
				if (index >= 0) {
					this.selecValue = item.value;
				} else {
					this.selecValue = ""
				}
			},
		}
	}
</script>

<style>
	.pickerView {
		padding: 5px;
		margin: 5%;
		display: flex;
		justify-content: flex-start;
		background-color: #fff;
		opacity: 0.9;
		box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);
		width: 40%;
		font-size: 110%;
	}

	.divider {
		/* width: 100%; */
		height: 1px;
		margin: 3% 0%;
		background-color: #000000;
		opacity: 0.5;
	}

	.calendar {
		margin: 5%;
		box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);
	}

	.bill_title {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin: 5% 5%;
	}

	.list {
		padding: 10px 20px;
		background-color: rgba(242, 243, 245);
		/* opacity: 0.8; */
		width: 300px;
	}

	.scheduleShow {
		/* width: 90%; */
		background-color: #fff;
		border-radius: 10%;
		padding: 10px;
		/* border: 1px solid #000000; */
		/* box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2); */
	}

	.list .title {
		font-size: 120%;
		font-weight: bold;
		margin-bottom: 3%;
	}

	.navi {
		background-color: #56DFC0;
		box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);
		display: flex;
		justify-content: center;
		align-items: center;
		height: 80upx;
		margin: 0% 5%;
		opacity: 0.9;
		width: 50%;
	}

	.b_title {
		margin: 5% 0% 0%;
		padding-left: 10px;
		font-size: 150%;
		font-weight: bold;
	}

	.b_text {
		padding: 0px;
		font-size: 20px;
		font-weight: bold;
		color: #000000;
	}

	.b_actions {
		display: flex;
		justify-content: flex-end;
		margin: 0% 3%;
	}

	.b_actions .button {
		background-color: #56DFC0;
		border-radius: 5%;
		box-shadow: 0 3px 8px #cac6;
		display: flex;
		justify-content: center;
		align-items: center;
		height: 32px;
		margin: 3% 0%;
		opacity: 0.9;
		width: 72px;
	}

	.strip {
		/* 父元素相对定位 */
		position: relative;
		width: 360rpx;
		height: 12rpx;
		color: rgba(80, 80, 80, 1);
		background-color: #d1d2d4;
		border-radius: 7rpx;
		font-size: 28rpx;
		line-height: 150%;
		text-align: center;
	}

	.blue {
		height: 12rpx;
		color: rgba(80, 80, 80, 1);
		background-color: #1676FE;
		border-radius: 7rpx;
		font-size: 28rpx;
		line-height: 150%;
		text-align: center;
	}

	.bill_list {
		margin: 5% 5%
	}

	.bill_card {
		/* background-color: #56DFC0; */
		box-shadow: 0 3px 8px #cac6;
		display: flex;
		flex-flow: row;
		justify-content: flex-start;
		align-items: center;
		/* margin: 5% 0%; */
		height: 180upx;
		opacity: 0.8;
		/* box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.1); */
		;
	}

	.bill_card .bill_img {
		background-color: white;
		width: 20%;
		margin: 3%;
		border-radius: 50%;
		height: 70%;
	}

	.bill_card .bill_name {
		/* width: 30%; */
		font-size: 120%;
	}

	.bill_card .bill_amount {
		width: 30%;
		/* margin-top: 8%; */
		margin-left: 10%;
		font-size: 120%;
	}
</style>