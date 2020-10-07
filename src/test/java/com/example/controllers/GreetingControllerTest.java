package com.example.controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GreetingControllerTest {
  private MockMvc mockMvc;

  @Rule
  public final JUnitRestDocumentation restDocumentation =
      new JUnitRestDocumentation("build/generated-snippets");

  @Before
  public void setUp() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(new GreetingController())
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(document("greeting/{method-name}",
                requestParameters(parameterWithName("hour").description("時刻: 必須入力(0-24)"))))
            .build();
  }

  @Test
  public void GoodMorning下限_正常系() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "5")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.greeting").value("good morning"));
  }

  @Test
  public void GoodMorning上限_正常系() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "15")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.greeting").value("good morning"));
  }

  @Test
  public void GoodNight下限1_正常系() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "0")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.greeting").value("good night"));
  }

  @Test
  public void GoodNight下限2_正常系() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "16")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.greeting").value("good night"));
  }

  @Test
  public void GoodNight上限1_正常系() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "4")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.greeting").value("good night"));
  }

  @Test
  public void doc() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "24")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.greeting").value("good night"))
        .andDo(document("greeting/{method-name}",
            responseFields(fieldWithPath("greeting").type(JsonFieldType.STRING)
                .description("hourが0-4,16-24の場合はgood night,5-15の場合はgood morning"))));
  }

  @Test
  public void 入力値範囲外_下限越え() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "-1")).andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void 入力値範囲外_上限超え() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "25")).andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void 入力値範囲外_数値外入力() throws Exception {
    this.mockMvc.perform(get("/greeting").param("hour", "123abc"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
