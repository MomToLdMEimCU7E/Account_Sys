<template>
	<view>
		<view class="inv-h-w">
			<block v-for="(item,index) in items" :key="index">
				<view :class="['inv-h',Inv== index?'inv-h-se':'']" @click="Inv=index">{{item}}</view>
			</block>

		</view>
		<view class="" v-show="Inv == 0">

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
				items: ['支出', '收入']
			}
		},
		methods: {
			changeTab(Inv) {
				that.navIdx = Inv;
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

	
</style>