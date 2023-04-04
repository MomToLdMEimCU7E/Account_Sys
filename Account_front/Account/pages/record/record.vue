<template>
	<view class="container">
		<view class="content">
	
			<view class="uni-padding-wrap uni-common-mt">
				<uni-segmented-control :current="current" :values="items" :style-type="styleType"
					:active-color="activeColor" @clickItem="onClickItem" />
			</view>
			<view class="swiper-item" :style="{height: scrollHeight}">
				<scroll-view :scroll-top="scrollTop" scroll-y="true" class="scroll-Y" @scrolltoupper="upper"
					@scrolltolower="lower" @scroll="scroll">
					<view class="icon-content">
						<view v-if="current === 0">
							<uni-grid :column="6" :square="false" :highlight="false" @change="change2">
								<uni-grid-item v-for="(item, index) in incomeList" :index="index" :key="index">
									<view class="grid-item-box">
										<uni-icons custom-prefix="icon-ledger" :color="item.color" :type="item.icon"
											size="30">
										</uni-icons>
									</view>
								</uni-grid-item>
							</uni-grid>
						</view>
						<view v-if="current === 1">
							<uni-grid :column="6" :square="false" :highlight="false" @change="change">
								<uni-grid-item v-for="(item, index2) in expendList" :index="index2" :key="index2">
									<view class="grid-item-box">
										<uni-icons custom-prefix="icon-ledger" :color="item.color" :type="item.icon"
											size="30">
										</uni-icons>
									</view>
								</uni-grid-item>
							</uni-grid>
						</view>
					</view>
				</scroll-view>
			</view>
			
		</view>
		<view class="keyboard-content">
			<uni-popup ref="popup" background-color="#fff">
				<view class="popup-content">
					<view class="remark-view-content">
						<uni-forms ref="baseForm" :modelValue="baseFormData">
							<uni-forms-item label="金额">
								<uni-easyinput v-model="num" type="digit" placeholder="请输入金额" />
								<!-- <input class="uni-input" v-model="num" type="digit" placeholder="请输入金额" /> -->
							</uni-forms-item>
							<uni-forms-item label="备注">
								<uni-easyinput v-model="remark" type="text" placeholder="请输入备注" />
								<!-- <input class="uni-input" v-model="remark" placeholder="请输入备注"></input> -->
							</uni-forms-item>
							<uni-forms-item label="日期">
								<uni-datetime-picker type="date" v-model="numData.date" />
							</uni-forms-item>
						</uni-forms>
						<button class="confim-btn" type="default" @click="done">提交</button>
					</view>
				</view>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
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
			    })
		},
		methods: {

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
	.content {
		margin-top: 20upx;
		padding-left: 20upx;
		padding-right: 20upx;

		.icon-content {
			margin-top: 20upx;
			width: 710upx;
		}
	}

	.keyboard-content {
		.popup-content {
			align-items: center;
			justify-content: center;
			padding: 25upx;
			height: 540upx;
			background-color: #fff;
		}
	}

	.confim-btn {
		height: 80upx;
		line-height: 80upx;
		border-radius: 20upx;
	}

	.icon-grid {
		// width: 700upx;
		background-color: aliceblue;
	}
</style>
