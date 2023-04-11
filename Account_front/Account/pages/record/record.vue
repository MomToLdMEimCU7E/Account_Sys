<template>
	<view class="container">
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
							<uni-grid :column="4" :square="false" :highlight="false" @change="change2" :show-border="false">
								<uni-grid-item v-for="(item, index) in payList" :index="index" :key="index">
									<view class="grid-item-box">
										<uni-icons custom-prefix="iconfont" :type="item.icon" size="40" color="#15D8AC">
										</uni-icons>
										<text style="padding-top: 20upx;">{{item.fundName}}</text>
									</view>
								</uni-grid-item>
							</uni-grid>
						</view>
						<view v-if="current === 1">
							<uni-grid :column="4" :square="false" :highlight="false" @change="change">
								<uni-grid-item v-for="(item, index2) in incomeList" :index="index2" :key="index2" :show-border="false">
									<view class="grid-item-box">
										<uni-icons custom-prefix="iconfont" :type="item.icon" size="30" color="#15D8AC">
										</uni-icons>
									</view>
								</uni-grid-item>
							</uni-grid>
						</view>
					</view>
				</scroll-view>
			</view>

		</view>

	</view>
</template>

<script>
	function getDate(date, AddDayCount = 0) {
		if (!date) {
			date = new Date()
		}
		if (typeof date !== 'object') {
			date = date.replace(/-/g, '/')
		}
		const dd = new Date(date)

		dd.setDate(dd.getDate() + AddDayCount) // 获取AddDayCount天后的日期

		const y = dd.getFullYear()
		const m = dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1 // 获取当前月份的日期，不足10补0
		const d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate() // 获取当前几号，不足10补0
		return {
			fullDate: y + '-' + m + '-' + d,
			year: y,
			month: m,
			date: d,
			day: dd.getDay()
		}
	}
	export default {
		data() {
			return {
				baseFormData: {},
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
			onClickItem(e) {
				if (this.current != e.currentIndex) {
					this.current = e.currentIndex;
				}
			},
			loadList() {
				uni.$http.get('/book/getFundIcon?bookkeepingTypeId=' + this.bookkeepingTypeId).then(res => {
					if (res.data.msg !== "成功")
						return uni.$showMsg()
					else{
						this.payList = res.data.data.data[1];
						this.incomeList = res.data.data.data[0];
					} 
						
					// console.log(this.bookList[0].bookkeepingCover)
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
	.selectCard{
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
</style>