import tw from "tailwind-styled-components"

function App() {
  return (
    <Test>안녕하세요</Test>
  )
}

export default App

const Test = tw.section`
  text-3xl
  font-bold
  underline
`
