var time = Date.parse(new Date()) / 1000
var info_state = {
	state:'',
	msg:''
}

class tools {
	//检查用户信息是否过期 0:未登录  1：用户信息正常   -1：用户信息不存在或已过期
	info(token,my_data){
		if(!token){
			return info_state = {
				state:0,
				msg:'未登录'
			}
		}else{
			if(my_data && (my_data.save_time+3600*2) > time ){
				info_state.state = 1
				info_state.msg = '用户信息未过期'
				return info_state
			}
			info_state.state = -1
			info_state.msg = '用户信息不存在或已过期'
			return info_state
		}
	}
}