<template>
	<view>
		<scroll-view scroll-y="true" :enable-flex="true" style="overflow: auto;">
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
				<text class="b_text">
					<view class="strip">
						<view class="blue" :style="'width:'+info.progress+'%'">
						</view>
					</view>
					<view style="margin-top: 5%;">
						剩余预算:{{left_budget}}
					</view>
					<view class="divider"></view>
				</text>
				<view slot="actions" class="b_actions">
					<view class="button">
						设置预算
					</view>
				</view>
			</uni-card>
			<view class="bill_title">
				<text style="font-size: 150%;font-weight: bold;">收支记录</text>
				<select-lay :value="selecValue" placeholder="筛选" name="name" :options="options" @selectitem="selectitem"
					style="width: 80px;background-color: #56DFC0;box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);font color: black;"></select-lay>
			</view>
			<view class="bill_list" v-for="(item,index) in payList" :key="index">
				<view class="bill_card" @click="GoToprodetail(index)">
					<img class="bill_img" :src="item.icon" mode="widthFix">
					<view class="bill_name">
						{{item.fundName}}
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
		<!--<uni-popup ref="addDate" background-color="#fff" type="bottom">
			<view class="pop">
				<uni-forms :modelValue="accountData" label-width="70px">
					 <uni-forms-item label="账户名称" name="accountDetailName">
						<uni-easyinput class="pop_in" type="text" v-model="accountData.accountDetailName"
							placeholder="请输入账户名称" />
					</uni-forms-item> 
					<uni-forms-item label="金额" name="balance">
						<uni-easyinput class="pop_in" prefixIcon="search" type="text" v-model="accountData.balance"
							placeholder="请输入金额" />
					</uni-forms-item>
					<uni-forms-item label="备注" name="comment">
						<uni-easyinput class="pop_in" type="text" v-model="accountData.comment" placeholder="请输入备注" />
					</uni-forms-item>
				</uni-forms>
			</view>
			<button @click="addpro()" type="primary">确认添加</button>
		</uni-popup> -->
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
				budget: 114514,
				left_budget: 14514,
				scheduleList: [],
				info: {
					progress: '',
				},
				date: '',
				options: [{
					value: '选项1',
					label: '娱乐'
				}, {
					value: '选项2',
					label: '日用'
				}, {
					value: '选项3',
					label: '人情'
				}, {
					value: '选项4',
					label: '伙食'
				}],
				c_date: {
					selected: [{
						date: '2023-04-03',
						info: '-700',
					}]
				},
				payList: [
					{
						icon: '../static/icon_p6208913rbe/youxi.png',
						fundName: '游戏',
						amount: '-100'
					}
				]
			}
		},
		onShow() {
			this.calucateProgress();
		},
		methods: {
			calucateProgress() {
				this.info.progress = this.budget / this.left_budget;
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
	.divider {
		width: 90%;
		height: 1px;
		margin: 3% 5%;
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
		background-color: rgba(242,243,245);
		/* opacity: 0.8; */
		width: 300px;
	}
	.scheduleShow{
		/* width: 90%; */
		background-color: #fff;
		border-radius: 10%;
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

	.divider {
		width: 100%;
		height: 1px;
		margin: 5% 0% 0%;
		background-color: #000000;
		opacity: 0.3;
	}

	.strip {
		/* 父元素相对定位 */
		position: relative;
		width: 360rpx;
		height: 12rpx;
		color: rgba(80, 80, 80, 1);
		background-color: #F5F5F5;
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
		width: 30%;
		font-size: 120%;
	}

	.bill_card .bill_amount {
		width: 30%;
		/* margin-top: 8%; */
		margin-left: 10%;
		font-size: 120%;
	}
</style>