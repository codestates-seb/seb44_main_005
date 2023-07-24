import { SiNotion, SiGithub } from 'react-icons/si';

import logo from '../../assets/logo.svg';
import { FooterSection } from '../../styles/Footer/Footer';

function Footer() {
  return (
    <FooterSection>
      <div>
        <div className="flex items-center mb-3">
          <img className="w-[100px]" src={logo} alt="로고" />
          <div className="ml-3">팀장: 김태우 | 부팀장: 강동우 | 팀원: 김민지, 현채은, 신이수</div>
        </div>
        <div className="mb-3 font-bold text-[#9b9b9b]">Copyright @ 2023 ActiOn</div>
        <div className="text-xs">
          상호명 (주)액티온 | 대표 김태우 |
          개인정보보호책임자 강동우 | 이메일 zop1234@hanmail.net |
          마케팅/제휴 문의 zop1234@hanmail.net | 광고 매체 문의 zop1234@hanmail.net
        </div>
      </div>
      <div className="flex">
        <a href="https://www.notion.so/codestates/IE5-8c32fc4f250f4bfea452017e93129644?pvs=4">
          <SiNotion className="mr-5" size="30" />
        </a>
        <a href="https://github.com/codestates-seb/seb44_main_005">
          <SiGithub size="30" />
        </a>
      </div>
    </FooterSection>
  );
}

export default Footer;
