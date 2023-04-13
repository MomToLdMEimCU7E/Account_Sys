<template>
	<view class="container">
		<view class="pickerView">
			<view style="margin: 0% 3%;">
				当前账本
			</view>
			<picker v-model="bookList.bookkeepingId" :range="bookList" range-key="bookkeepingName"
				:value="bookPickerIndex" @change="changeId">
				<view>{{bookList[bookPickerIndex].bookkeepingName}}</view>
			</picker>
		</view>
		<view class="content">
			<view class="selectCard">
				<uni-segmented-control :current="current" :values="items" style-type="button" active-color="#15D8AC"
					@clickItem="onClickItem" />
			</view>
			<view class="swiper-item" :style="{height: scrollHeight}">
				<scroll-view :scroll-top="scrollTop" scroll-y="true" class="scroll-Y" @scrolltoupper="upper"
					@scrolltolower="lower" @scroll="scroll">
					<view class="icon-content">
						<view v-if="current === 0">
							<uni-grid :column="4" :square="false" :highlight="false" @change="change2"
								:show-border="false">
								<uni-grid-item v-for="(item, index) in payList" :index="index" :key="index">
									<view class="grid-item-box">
										<uni-icons custom-prefix="iconfont" :type="item.icon" size="40" color="#15D8AC">
										</uni-icons>
										<text style="padding-top: 20upx;">{{item.fundName}}</text>
									</view>
								</uni-grid-item>
							</uni-grid>
							<view class="navi" @click="openAddbookDialog()" style="margin-bottom: 5%;">
								<uni-icons type="plusempty"></uni-icons>文本识别
							</view>
							<view class="navi" @click="openAddbookDialog()">
								<uni-icons type="camera-filled"></uni-icons>图片识别
							</view>
						</view>
						<view v-if="current === 1">
							<uni-grid :column="4" :square="false" :highlight="false" @change="change"
								:show-border="false">
								<uni-grid-item v-for="(item, index2) in incomeList" :index="index2" :key="index2">
									<view class="grid-item-box">
										<uni-icons custom-prefix="iconfont" :type="item.icon" size="40" color="#15D8AC">
										</uni-icons>
										<text style="padding-top: 20upx;">{{item.fundName}}</text>
									</view>
								</uni-grid-item>
							</uni-grid>
							<view class="navi" @click="openAddbookDialog()">
								<uni-icons type="plusempty"></uni-icons>智能识别
							</view>
						</view>
					</view>
				</scroll-view>
			</view>

		</view>
		<uni-popup ref="norpopup" background-color="#fff" type="bottom">
			<view class="pop">
				<uni-forms :modelValue="recordData" label-width="70px">
					<uni-forms-item label="金额" name="amount">
						<uni-easyinput class="pop_in" type="text" v-model="recordData.amount" placeholder="请输入金额" />
					</uni-forms-item>
					<uni-forms-item label="备注" name="comment">
						<uni-easyinput class="pop_in" type="text" v-model="recordData.comment" placeholder="请输入备注" />
					</uni-forms-item>
					<uni-forms-item label="日期时间">
						<uni-datetime-picker type="datetime" return-type="timestamp"
							v-model="recordData.time" />
					</uni-forms-item>
				</uni-forms>
			</view>
			<button @click="addpro()" type="primary">确认添加</button>
		</uni-popup>
		<uni-popup ref="intpopup" background-color="#fff" type="bottom">
			<view class="pop">
				<uni-forms :modelValue="recordData" label-width="70px">
					<uni-forms-item label="金额" name="amount">
						<uni-easyinput class="pop_in" type="text" v-model="recordData.amount" placeholder="请输入金额" />
					</uni-forms-item>
					<uni-forms-item label="备注" name="comment">
						<uni-easyinput class="pop_in" type="text" v-model="recordData.comment" placeholder="请输入备注" />
					</uni-forms-item>
					<uni-forms-item label="日期时间">
						<uni-datetime-picker type="datetime" return-type="timestamp"
							v-model="recordData.time" />
					</uni-forms-item>
				</uni-forms>
			</view>
			<button @click="addpro()" type="primary">确认添加</button>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				recordData: {
					uid: '',
					bookkeepingId: '',
					accountDetailId: '',
					amount: '',
					fundId: '',
					time: '',
					commet: ''
				},
				bookList: [],
				bookPickerIndex: 0,
				uid: 1,
				bookkeepingId: 1,
				bookkeepingTypeId: 1,
				scrollHeight: '',
				date: '',
				current: 0,
				scrollTop: 0,
				items: ['支出', '收入'],
				payList: [],
				incomeList: [],
			}
		},
		onShow() {
			this.loadList();
			this.getBookList();
		},
		onReady() {
			// 计算scroll-view高度
			uni.getSystemInfo({
				success: resu => {
					const query = uni.createSelectorQuery()
					query.select('.swiper-item').boundingClientRect()
					query.exec(res => {
						this.scrollHeight = resu.windowHeight - res[0].top + 'px';
						// console.log('打印页面的剩余高度', this.scrollHeight);
					})
				}
			});
			this.date = getDate(new Date(), 0).fullDate;
		},
		methods: {
			getBookList() {
				uni.$http.get('/book/getBookList?uid=' + this.uid).then(res => {
					const myrange = [];
					if (res.data.msg !== "成功")
						return uni.$showMsg()
					else {
						this.bookList = res.data.data.data;
						this.bookkeepingTypeId = this.bookList[0].bookkeepingTypeId;
					}

					// console.log(this.bookList[0].bookkeepingId)
				})
			},
			changeId(e) {
				this.bookPickerIndex = e.detail.value;
				this.bookkeepingTypeId = this.bookList[this.bookPickerIndex].bookkeepingTypeId;
				this.loadList();
			},
			onClickItem(e) {
				if (this.current != e.currentIndex) {
					this.current = e.currentIndex;
				}
			},
			change2(e) {
				this.$refs.norpopup.open();
				let {
					index
				} = e.detail
				this.recordData.fundId = this.payList[index].fundId
				// console.log(this.payList[index].fundId)
			},
			openAddbookDialog(){
				this.$refs.intpopup.open();
				
			},
			loadList() {
				uni.$http.get('/book/getFundIcon?bookkeepingTypeId=' + this.bookkeepingTypeId).then(res => {
					if (res.data.msg !== "成功")
						return uni.$showMsg()
					else {
						this.payList = res.data.data.data[1];
						this.incomeList = res.data.data.data[0];
					}

					console.log(this.payList[0].fundId)
				})
			}
		}
	}
</script>

<style>
	/* #ifdef MP-WEIXIN || APP-PLUS*/
	::-webkit-scrollbar {
		display: none;
	}

	/* #endif */
	/* #ifdef H5*/
	uni-scroll-view .uni-scroll-view::-webkit-scrollbar {
		display: none;
	}

	/* #endif */
	.pickerView {
		padding: 5px;
		margin: 5%;
		display: flex;
		justify-content: flex-start;
		background-color: #fff;
		opacity: 0.8;
		box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);
		width: 40%;
		font-size: 110%;
	}

	.selectCard {
		margin: 5%;
	}

	.icon-content {
		/* padding-left: 20upx;
		padding-right: 20upx; */
		/* margin: 0% 5%; */
		margin-left: 125upx;
		margin-right: 125upx;
		/* width: 600upx; */
	}

	.grid-item-box {
		flex: 1;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin: 3%;
		padding: 15px;
	}

	.grid-item-box-row {
		flex: 1;
		// position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		padding: 15px 0;
	}

	.navi {
		background-color: #56DFC0;
		box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);
		display: flex;
		justify-content: center;
		align-items: center;
		height: 75upx;
		margin: 0% 5%;
		opacity: 0.9;
	}

	.pop {
		padding: 20px;
		display: flex;
		justify-content: space-between;
		/* align-items: center; */
	}
</style>