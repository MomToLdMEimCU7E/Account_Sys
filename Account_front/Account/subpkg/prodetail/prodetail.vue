<template>
	<view>
		<view class="p_list">
			<view class="p_card">
				<img class="c_img" src="../../static/icon_p6208913rbe/weixin.png" mode="widthFix">
				<view class="c_name">
					{{proDetail.accountDetailName}}
					<view class="c_amount">
						￥{{proDetail.balance}}
					</view>
				</view>
			</view>
			<view class="divider"></view>
			<view class="c_comment">
				备注信息
			</view>
			<view class="c_text">
				无
			</view>
		</view>
		<view class="navi" @click="dialogToggle('info')">
			<uni-icons type="trash-filled" size="25"></uni-icons>删除账户
		</view>
		<uni-popup ref="alertDialog" type="dialog">
			<uni-popup-dialog type="error" cancelText="取消" confirmText="确定" title="请注意!" content="确定删除该账户吗"
				@confirm="deleteConfirm"></uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				proDetail: [],
			}
		},
		onLoad: function(option) {
			const item = JSON.parse(decodeURIComponent(option.item));
			this.proDetail = item;
			// console.log(this.proDetail);
		},
		methods: {
			dialogToggle() {
				this.$refs.alertDialog.open()
				// console.log(this.proDetail.accountDetailId)
			},
			deleteConfirm() {
				uni.$http.put('/account/deleteAccountDetail?accountDetailsId=' + this.proDetail.accountDetailId).then(res => {
					if (res.data.msg === "成功")
						return uni.showToast({
							title: '删除成功！',
							duration: 1500,
							icon: 'none',
						})
					else return uni.showToast({
						title: '删除失败！',
						duration: 1500,
						icon: 'none',
					})
				})
				uni.reLaunch({
					url: '/subpkg/property/property'
				})
			},

		}
	}
</script>

<style>
	.p_list {
		margin: 5% 5%;
		background-color: #56DFC0;
		display: flex;
		justify-content: start;
		align-items: center;
		flex-wrap: wrap;
		opacity: 0.9;
		box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.1);
	}

	.p_card {
		display: flex;
		justify-content: start;
		align-items: center;
		flex-wrap: wrap;
		/* margin: 5% 0%; */
		/* height: 180upx; */

	}

	.p_card .c_img {
		background-color: white;
		width: 20%;
		margin: 3%;
		border-radius: 50%;
		height: 70%;
	}

	.p_card .c_name {
		width: 30%;
		font-size: 120%;
	}

	.p_card .c_amount {
		width: 30%;
		margin-top: 10%;
		/* margin-left: 10%; */
		font-size: 200%;
		/* color: #F59DAD; */
	}

	.divider {
		width: 90%;
		height: 1px;
		margin: 3% 5%;
		background-color: #000000;
		opacity: 0.5;
	}

	.c_comment {
		font-size: 120%;
		font-weight: bold;
		margin-left: 5%;
		width: 100%;
	}

	.c_text {
		margin: 2% 5%;
		font-size: 110%;
	}

	.navi {
		background-color: #E65452;
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: 110%;
		height: 100upx;
		margin: 0% 5%;
		/* opacity: 0.9; */
	}
</style>