<template>
	<view>
		<view class="a_view">
			<uni-section title="普通账户" type="line" class="a_section"></uni-section>
			<view class="a_item">
				<view class="a_tab" @click="addpro1()">
					<uni-icons custom-prefix="iconfont" type="icon-_xianjin" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">现金</text>
				</view>
				<view class="a_tab" @click="addpro2()">
					<uni-icons custom-prefix="iconfont" type="icon-xinyongka1" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">储蓄卡</text>
				</view>
				<view class="a_tab" @click="addpro3()">
					<uni-icons custom-prefix="iconfont" type="icon-weixin" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">微信钱包</text>
				</view>
				<view class="a_tab" @click="addpro4()">
					<uni-icons custom-prefix="iconfont" type="icon-zhifubao" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">支付宝</text>
				</view>
				<view class="a_tab" @click="addpro1()">
					<uni-icons type="plus" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">其它</text>
				</view>
			</view>
			<uni-section title="信用账户" type="line" class="a_section"></uni-section>
			<view class="a_item">
				<view class="a_tab" @click="addpro5()">
					<uni-icons custom-prefix="iconfont" type="icon-xinyongka" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">信用卡</text>
				</view>
				<view class="a_tab" @click="addpro6()">
					<uni-icons custom-prefix="iconfont" type="icon-huabei" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">花呗</text>
				</view>
				<view class="a_tab" @click="addpro5()">
					<uni-icons type="plus" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">其它</text>
				</view>
			</view>
			<uni-section title="投资账户" type="line" class="a_section"></uni-section>
			<view class="a_item">
				<view class="a_tab" @click="addpro7()">
					<uni-icons custom-prefix="iconfont" type="icon-gupiao" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">股票</text>
				</view>
				<view class="a_tab" @click="addpro8()">
					<uni-icons custom-prefix="iconfont" type="icon-gupiao" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">基金</text>
				</view>
				<view class="a_tab" @click="addpro7()">
					<uni-icons type="plus" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">其它</text>
				</view>
			</view>
			<uni-section title="充值账户" type="line" class="a_section"></uni-section>
			<view class="a_item">
				<view class="a_tab" @click="addpro9()">
					<uni-icons custom-prefix="iconfont" type="icon-shouye" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">饭卡</text>
				</view>
				<view class="a_tab" @click="addpro10()">
					<uni-icons custom-prefix="iconfont" type="icon-gongjiaoka" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">公交卡</text>
				</view>
				<view class="a_tab" @click="addpro11()">
					<uni-icons custom-prefix="iconfont" type="icon-huiyuanka" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">会员卡</text>
				</view>
				<view class="a_tab" @click="addpro9()">
					<uni-icons type="plus" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">其它</text>
				</view>
			</view>
			<uni-section title="其它" type="line" class="a_section"></uni-section>
			<view class="a_item">
				<view class="a_tab" @click="addpro12()">
					<uni-icons custom-prefix="iconfont" type="icon-jiekuan" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">借入</text>
				</view>
				<view class="a_tab" @click="addpro13()">
					<uni-icons custom-prefix="iconfont" type="icon-jiechu" size="30" color="#15D8AC"></uni-icons>
					<text class="a_title">借出</text>
				</view>
			</view>
		</view>
		<uni-popup ref="popup" background-color="#fff" type="bottom">
			<view class="pop">
				<uni-forms :modelValue="accountData" label-width="70px">
					<!-- <uni-forms-item label="账户名称" name="accountDetailName">
						<uni-easyinput class="pop_in" type="text" v-model="accountData.accountDetailName"
							placeholder="请输入账户名称" />
					</uni-forms-item> -->
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
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				accountData: {
					uid: '1',
					accountDetailTypeId: '',
					accountDetailName: '',
					balance: '',
					comment: '',
				},
			}
		},
		methods: {
			addpro() {
				uni.$http.post('/account/addAccountDetail', this.accountData).then(res => {
					if (res.data.msg === "成功")
						return uni.showToast({
							title: '添加成功！',
							duration: 1500,
							icon: 'none',
						})
					else return uni.showToast({
						title: '添加失败！',
						duration: 1500,
						icon: 'none',
					})
				})

				this.$refs.popup.close()
			},
			addpro1() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 1;
			},
			addpro2() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 2;
			},
			addpro3() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 1;
			},
			addpro4() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 4;
			},
			addpro5() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 5;
			},
			addpro6() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 6;
			},
			addpro7() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 7;
			},
			addpro8() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 8;
			},
			addpro9() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 9;
			},
			addpro10() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 10;
			},
			addpro11() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 11;
			},
			addpro12() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 12;
			},
			addpro13() {
				this.$refs.popup.open("bottom");
				this.accountData.accountDetailTypeId = 13;
			},
		}
	}
</script>

<style>
	.a_section {
		background-color: #EEF8F8;
	}

	.a_item {
		display: flex;
		flex-direction: row;
		/* height: 200upx; */
		/* background-color: #fff; */
	}

	.a_tab {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 10px 15px;
	}

	.a_title {
		font-size: 14px;
		padding-top: 10px;
		/* color: #919293; */
		padding-bottom: 8px;
	}

	.pop {
		padding: 20px;
		display: flex;
		justify-content: space-between;
		/* align-items: center; */
	}

	.pop_in {
		/* margin: 5%; */
	}
</style>