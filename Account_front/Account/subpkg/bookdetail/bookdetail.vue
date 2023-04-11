<template>
	<view>
		<view class="bookShow" v-if="upbookData">
			<image :src="'../../static/' + bookDetail.bookkeepingCover +'.png'" mode="scaleToFill"
				style="width: 250upx;height: 350upx;"></image>
				<view class="titleShow">
					<uni-icons type="compose" size="30" color="#15D8AC" @click="changeNameDialog()"></uni-icons>
					<view class="title">{{bookDetail.bookkeepingName}}</view>
				</view>
			<view class="button">
				更改封面
			</view>
		</view>
		<uni-popup ref="nameDialog" type="dialog">
			<uni-popup-dialog type="info" cancelText="取消" confirmText="确定" title="更改账户名称"
				@confirm="changeNameConfirm()">
				<uni-forms :modelValue="updateBook">
					<uni-easyinput v-model="updateBook.bookkeepingName" placeholder="请输入账本名称"></uni-easyinput>
				</uni-forms>
				</uni-popup-dialog>
		</uni-popup>
		<uni-card title="基础卡片" v-if="upbudgetData">
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
	</view>
</template>

<script>
	export default {
		data() {
			return {
				bookDetail: [],
				updateBook: {
					bookkeepingId: '',
					bookkeepingName: '',
					bookkeepingCover: '',
				},
				updateBudget: {
					bookkeepingId: '',
					bookkeepingName: '',
					bookkeepingCover: '',
					budget: '',
				},
				upbookData: true,
				upbudgetData: true,
				budget: '',
				left_budget: 200,
				info: {
					progress: '',
				},
			}
		},
		onLoad: function(option) {
			const item = JSON.parse(decodeURIComponent(option.item));
			this.bookDetail = item;
			this.updateBudget = item;
			// console.log(this.updateBook);
		},
		onShow() {
			// this.calucateProgress();
			// this.getBudget();
			this.init();
		},
		methods: {
			init(){
				this.calucateProgress();
				this.getBudget();
			},
			async calucateProgress() {
				await this.getBudget().then((res)=>{
					this.info.progress = this.left_budget / this.budget * 100 ;
					// console.log(this.info.progress)
				})
			},
			async getBudget() {
				const {
					data: res
				} = await uni.$http.get('/book/getBudget?bookkeepingId=' + this.bookDetail.bookkeepingId)
				if (res.msg !== "成功") return uni.$showMsg()
				this.budget = res.data
				// console.log(res.data)
			},
			changeNameDialog(){
				this.$refs.nameDialog.open();
				Object.assign(this.updateBook,this.bookDetail);
				this.updateBook.budget = this.budget; 
				// this.upbookData = this.bookDetail;
			},
			changeNameConfirm(){
				uni.$http.post('/book/updateBook', this.updateBook).then(res => {
					if (res.data.msg === "成功"){
						// this.init();
						this.bookDetail.bookkeepingName = this.updateBook.bookkeepingName
						return uni.showToast({
							title: '修改成功！',
							duration: 1500,
							icon: 'none',
						});
						
					}
						
					else return uni.showToast({
						title: '修改失败！',
						duration: 1500,
						icon: 'none',
					})
				})
				
				this.$refs.nameDialog.close()
			},
			changeBudgetDialog(){
				this.$refs.budgetDialog.open();
				// Object.assign(this.updateBudget.budget,this.budget);
				this.updateBudget.budget = this.budget; 
				console.log(this.updateBudget.budget)
			},
			changeBudgetConfirm(){
				uni.$http.post('/book/updateBook', this.updateBudget).then(res => {
					// console.log(res.data.msg);
					if (res.data.msg === "成功"){
						this.init();
						return uni.showToast({
							title: '修改成功！',
							duration: 1500,
							icon: 'none',
						});
						// this.upbudgetData = false;
						// this.upbudgetData = true;
						 // this.$forceUpdate() 
						 
					}
					else return uni.showToast({
						title: '修改失败！',
						duration: 1500,
						icon: 'none',
					})
				})
				
				this.$refs.budgetDialog.close()
			}
		}
	}
</script>

<style>
	.bookShow{
		display: flex;
		background-color: #fff;
		box-shadow: 0 3px 8px #cac6;
		padding: 10px;
		margin: 5%;
		justify-content: space-around;
	}
	.titleShow{
		display: flex;
		justify-content: center;
		/* align-items: center; */
	}
	.titleShow .title{
		font-size: 130%;
		font-weight: bold;
		margin-left: 5%;
		width: 120px;
	}
	.bookShow .button {
		background-color: #56DFC0;
		border-radius: 5%;
		box-shadow: 0 3px 8px #cac6;
		display: flex;
		justify-content: center;
		align-items: center;
		align-self: flex-end;
		height: 32px;
		margin: 0%;
		opacity: 0.9;
		width: 72px;
	}
	.divider {
		/* width: 100%; */
		height: 1px;
		margin: 3% 0%;
		background-color: #000000;
		opacity: 0.5;
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
</style>