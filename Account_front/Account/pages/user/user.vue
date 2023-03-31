<template>
	<view>
		<view class="content-item">
			<view class="user-info">
				<view>
					<image src="/static/user.jpg" class="img" :mode="aspectFit"></image>
				</view>
				<view class="name">
					<text>{{userName}}</text>
				</view>
			</view>
		</view>
		<bxTabber></bxTabber>
		<view class="content-item">
			<view class="content-show">
				<view class="money-item">
					<text>
						{{accountDay}}
					</text>
					<text style="font-size: 12px;color: #919293;">
						记账天数
					</text>
				</view>
				<view class="money-item">
					<text>
						{{accountCount}}
					</text>
					<text style="font-size: 12px;color: #919293;">
						记账笔数
					</text>
				</view>
			</view>

		</view>
		<view class="content-item">
			<view class="content-show">
				<view class="order-tab">
					<uni-icons type="calendar" size="30" color="#15D8AC"></uni-icons>
					<text class="order-title">记账年报</text>
				</view>
				<navigator url="/subpkg/property/property">
					<view class="order-tab">
						<uni-icons type="wallet"  size="30" color="#15D8AC"></uni-icons>
						<text class="order-title">资产管理</text>
					</view>
				</navigator>
			
				<view class="order-tab">
					<uni-icons type="icon-yiliao" custom-prefix="iconfont" size="30" color="#15D8AC"></uni-icons>
					<text class="order-title">健康管理</text>
				</view>
			</view>
		</view>
		<view class="content-item">
			<view class="title">
				<text>我的账本</text>
			</view>
			<view class="content-show">
				<view class="service-item">
					<uni-icons type="gear" size="35" color="#15D8AC"></uni-icons>
					<text class="service-text">设置</text>
				</view>
			</view>
		</view>
		<uni-card :isShadow="true" title="设置" mode="basic" @click="clickCard">
			<view class="settingbox">
				<text class="switchTx">老年模式</text>
				<text class="switchBu">
					<tm-switch v-model="on"></tm-switch>
				</text>
			</view>
		</uni-card>
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
				accountCount: '111',
				accountDay: '111',
				userName: '测试用户',
			}
		},
		methods: {

		}
	}
</script>

<style>
	.container {
		width: 100%;
	}

	.profile {
		width: 100%;
	}

	.user-info {
		/* position: absolute;
		top: 100px;
		left: 30px; */
		display: flex;
		align-items: center;
		background-color: #13D0A5;
		height: 250upx;
		justify-content: flex-start;
	}
	.user-info .img{
		margin: 5% 10%;
		width: 200upx;
		height: 200upx;
		border-radius: 50%;
	}
	.user-info .name {
		margin-left: 5%;
		font-size: 120%;
		/* display: flex; */
	}

	.content-item {
		display: flex;
		margin: 10px;
		flex-direction: column;
		border-radius: 4px;
		background-color: #FFFFFF;
		justify-content: space-around;
	}

	.content-item .title {
		padding: 15px;
		color: #5A5B5C;
		font-size: 16px;
		font-weight: bold;
	}

	.content-show {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
	}

	.money-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 20px;
	}

	.service-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 20px;
	}

	.service-item .service-text {
		font-size: 12px;
		color: #919293;
	}

	.order-tab {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 15px;
	}

	.order-title {
		font-size: 14px;
		padding-top: 10px;
		color: #919293;
		padding-bottom: 8px;
	}

	.settingbox {
		flex-flow: row;
		justify-content: flex-start;
		display: flex;
	}
	.switchTx{
		display: flex;
		justify-content: flex-start;
		width: 200%;
	}
	.switchBu{
		display: flex;
		justify-content: flex-end;
	}
</style>
