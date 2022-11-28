package com.be.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.be.dto.CVSubmitDTO;
import com.be.dto.EmployerDTO;
import com.be.dto.PostDTO;
import com.be.dto.ProfileDTO;
import com.be.dto.UserDTO;
import com.be.model.CVSubmit;
import com.be.model.Employer;
import com.be.model.Post;
import com.be.model.Profile;
import com.be.model.User;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMaper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		modelMapper.createTypeMap(Employer.class, EmployerDTO.class).addMappings(mapper -> {
	//		mapper.map(src -> src.getField().getName(), (dst, value) -> dst.setFieldName((String) value));
//			mapper.map(src -> src.getCity().getName(), (dst, value) -> dst.setCity((String) value));
			mapper.map(src -> src.getAvatar().getUrl(), (dst, value) -> dst.setAvatar((String) value));

		});

		modelMapper.createTypeMap(User.class, UserDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getAvatar().getUrl(), (dst, value) -> dst.setAvatar((String) value));
		});
		modelMapper.createTypeMap(Profile.class, ProfileDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getMediaResource().getUrl(), (dst, value) -> dst.setUrl((String) value));
			mapper.map(src -> src.getMediaResource().getId(), (dst, value) -> dst.setMediaId((Long) value));

		});
		modelMapper.createTypeMap(Post.class, PostDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getAvatar().getUrl(), (dst, value) -> dst.setAvatar((String) value));
			mapper.map(src -> src.getCity().getName(), (dst, value) -> dst.setCity((String) value));
			mapper.map(src -> src.getField().getName(), (dst, value) -> dst.setField((String) value));
			mapper.map(src -> src.getAcceptedBy().getEmail(), (dst, value) -> dst.setAdminAceptedEmail((String) value));

		});

		modelMapper.createTypeMap(CVSubmit.class, CVSubmitDTO.class).addMappings(mapper -> mapper
				.map(src -> src.getProfile().getMediaResource().getUrl(), (dst, value) -> dst.setUrl((String) value)));
		return modelMapper;
	}
}
