<template>
	<tm-bottomnavigation icon-color="blue" :list="list_1" :vertical="true"></tm-bottomnavigation>
</template>
<script>
	export default {
		name: "bxTabber", //组件的名称
		data() {
			return {
				/*组件的数据*/
				list_1: [{
						iconSize: 45,
						fontSize: 25,
						path: '/pages/home/home',
						icon: '../../static/icon_a660xqpf7bk/shouye.png',
						value: '首页',
						// openType: 'redirect'
					},
					{
						iconSize: 45,
						fontSize: 25,
						path: '/pages/bill/bill',
						icon: '../../static/icon_a660xqpf7bk/zhangdan.png',
						value: '账单',
						// openType: 'redirect'
					},
					{
						iconSize: 60,
						fontSize: 30,
						path: '/pages/record/record',
						// icon: '../../static/icon_a660xqpf7bk/zengjiatianjiajiahao.png',
						icon: 'icon-plus-circle-fill',
						value: '记一笔',
						custom:true,
						customColor: 'white',
						customFontcolor: 'black',
						openType: 'navigate'
					},
					{
						iconSize: 45,
						fontSize: 25,
						path: '/pages/count/count',
						icon: '../../static/icon_a660xqpf7bk/tongji.png',
						value: '统计',
						// openType: 'redirect'
					},
					{
						iconSize: 45,
						fontSize: 25,
						path: '/pages/user/user',
						icon: '../../static/icon_a660xqpf7bk/jurassic_user.png',
						value: '我的',
						// openType: 'redirect'
					}
				],
			}
		}
	}
</script>
<style>
</style>
