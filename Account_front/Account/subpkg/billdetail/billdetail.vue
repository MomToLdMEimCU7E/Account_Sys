<template>
	<view>
		<view class="Titlearea">
			<view class="title">
				{{items[index]}}
			</view>
			<view class="title">
				{{billDetail.amount}}
			</view>
		</view>
		<view class="divider"></view>
		<view class="infoarea">
			<view class="info">
				<text>分类</text>
				{{billDetail.fundName}}
			</view>
			<view class="info">
				<text>时间</text>
				{{billDetail.time}}
			</view>
			<view class="info">
				<text>备注</text>
				{{billDetail.commet}}
			</view>
			<view class="info">
				<text>支付账户</text>
				{{billDetail.accountDetailName}}
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				billDetail: [],
				items: ['支出', '收入'],
				index: 0,
			}
		},
		onLoad: function(option) {
			const item = JSON.parse(decodeURIComponent(option.item));
			this.billDetail = item;
			if(this.billDetail.amount.charAt(0) == '-') this.index = 0;
			else this.index = 1;
			console.log(this.billDetail.amount.charAt(0));
		},
		methods: {

		}
	}
</script>

<style>
	.Titlearea{
		margin: 10% 5% 0%;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		align-items: center;
	}
	.Titlearea .title{
		font-size: 200%;
		font-weight: bold;
		padding: 10px;
	}
	.divider {
		width: 90%;
		height: 1px;
		margin: 5% 5%;
		background-color: #000000;
		opacity: 0.7;
	}
	.infoarea{
		margin: 20% 5% 0%;
		font-size: 150%;
	}
	.infoarea .info{
		display: flex;
		justify-content: space-between;
		margin: 3%;
	}
</style>