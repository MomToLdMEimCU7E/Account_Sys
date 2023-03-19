<template>
	<view>
		<tm-sheet :shadow="24" :padding="[0,0]">
			<!-- <view class="text-size-s text-weight-b pa-32 ">嵌入式日历</view> -->
			<tm-calendarView title="" :default-value.sync="ceshit" :bing-start.sync="bts" :bing-end.sync="ets"
				color="bg-gradient-pink-accent" btn-text="日程提醒" @click="scheduleDialogBisible=true"></tm-calendarView>
		</tm-sheet>
		<tm-card 
		title="总预算" 
		content="只有内容和按钮的卡片" 
		:titleBorder="false" 
		:action="['设置预算']" 
		:btnColor="['primary']"
			@click="handleClick()">
			<template v-slot:title="{ text }">
				<view>
					<view class="py-24">月度总预算:{{ amount }}</view>
				</view>
			</template>
			<template v-slot:content="{ text }">
				<view>
					<view class="strip">
						<view class="blue" :style="'width:'+info.progress+'%'">
						</view>
					</view>
					<view>
						剩余预算:
					</view>
					<view class="">
						本月预算:
					</view>
				</view>
			</template>
		</tm-card>
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
				amount: 11415,
				info: {
					progress: 32,
				}
			}
		},
		methods: {

		}
	}
</script>

<style>
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
</style>
